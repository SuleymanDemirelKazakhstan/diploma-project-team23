package kz.sdu.project.sauapbackend.service.impl;

import kz.sdu.project.sauapbackend.dto.UserDto;
import kz.sdu.project.sauapbackend.dto.request.ChangePasswordDto;
import kz.sdu.project.sauapbackend.dto.request.UpdateUserDto;
import kz.sdu.project.sauapbackend.dto.response.CheckPasswordResponseDto;
import kz.sdu.project.sauapbackend.dto.response.ServerDefaultResponse;
import kz.sdu.project.sauapbackend.dto.response.UpdateImageResponseDto;
import kz.sdu.project.sauapbackend.dto.response.UserInfoDto;
import kz.sdu.project.sauapbackend.entity.Users;
import kz.sdu.project.sauapbackend.exception.ErrorCode;
import kz.sdu.project.sauapbackend.exception.ValidationException;
import kz.sdu.project.sauapbackend.mapper.UserMapper;
import kz.sdu.project.sauapbackend.repository.UsersRepository;
import kz.sdu.project.sauapbackend.service.AmazonS3Service;
import kz.sdu.project.sauapbackend.service.UsersService;
import kz.sdu.project.sauapbackend.service.ValidatorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class UsersServiceImpl implements UsersService {
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final ValidatorService<UserDto> validator;
    private final AmazonS3Service amazonS3Service;

    private static final String USER_IMAGES_FOLDER = "/users/images";

    @Autowired
    public UsersServiceImpl(UsersRepository usersRepository,
                            ValidatorService<UserDto> validator,
                            AmazonS3Service amazonS3Service) {
        this.usersRepository = usersRepository;
        this.validator = validator;
        this.amazonS3Service = amazonS3Service;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public ServerDefaultResponse registerNewAccount(UserDto request) {
        try {
            validator.validate(request);

            request.setPassword(passwordEncoder.encode(request.getPassword()));
            request.setIsActive(false);
            Users users = UserMapper.toEntity(request);
            usersRepository.saveAndFlush(users);

            ServerDefaultResponse response = new ServerDefaultResponse();
            response.setIsDone(true);
            response.setProcessTime(LocalDateTime.now());
            response.setMessage(null);
            return response;
        } catch (Exception ex) {
            if (ex instanceof ValidationException) {
                throw ex;
            }
            throw new RuntimeException("Service Error");
        }
    }

    @Override
    public UserInfoDto getUserByEmail(String email) {
        Optional<Users> user = usersRepository.findByEmail(email);
        if (user.isPresent()) {
            return UserMapper.toDto(user.get());
        }
        throw new ValidationException(String.format("The user was not found by this email: %s", email), ErrorCode.USER_NOT_FOUND);
    }

    @Override
    public void updateUser(UpdateUserDto userDto, String email) {
        if (Objects.isNull(email) || email.isEmpty()) {
            throw new ValidationException("'email' field is empty", ErrorCode.FIELD_IS_INVALID);
        }

        Optional<Users> usersOptional = usersRepository.findByEmail(email);
        Users userFromDb = usersOptional.orElseThrow(() -> new ValidationException("User is not found", ErrorCode.USER_NOT_FOUND));
        UserMapper.toEntity(userDto, userFromDb);

        usersRepository.saveAndFlush(userFromDb);
        log.info("USER SERVICE | update user successfully updated | {}", userFromDb);
    }

    @Override
    public CheckPasswordResponseDto checkUserPassword(String email, String password) throws ValidationException {
        Optional<Users> usersOptional = usersRepository.findByEmail(email);
        if (usersOptional.isEmpty()) {
            throw new ValidationException("The user was not found by this email", ErrorCode.USER_NOT_FOUND);
        }

        CheckPasswordResponseDto checkPasswordResponseDto = new CheckPasswordResponseDto();
        checkPasswordResponseDto.setMatch(passwordEncoder.matches(password, usersOptional.get().getPassword()));
        return checkPasswordResponseDto;
    }

    @Override
    public ServerDefaultResponse changeUserPassword(ChangePasswordDto passwordDto) {
        ServerDefaultResponse serverDefaultResponse = new ServerDefaultResponse();
        try {
            Optional<Users> usersOptional = usersRepository.findByEmail(passwordDto.getEmail());
            validatePassword(passwordDto, usersOptional);

            String newEncodedPassword = passwordEncoder.encode(passwordDto.getNewPassword());
            Users users = usersOptional.get();
            users.setPassword(newEncodedPassword);
            usersRepository.saveAndFlush(users);

            serverDefaultResponse.setIsDone(true);
            serverDefaultResponse.setMessage(null);
            serverDefaultResponse.setProcessTime(LocalDateTime.now());
            return serverDefaultResponse;
        } catch (Exception ex) {
            serverDefaultResponse.setIsDone(false);
            if (ex instanceof ValidationException) {
                serverDefaultResponse.setMessage(((ValidationException) ex).getMessage());
            } else {
                log.error("CHANGE PASSWORD ERROR | Error while change user password | {}", passwordDto, ex);
                serverDefaultResponse.setMessage("Internal Service Error");
            }
            serverDefaultResponse.setProcessTime(LocalDateTime.now());
            return serverDefaultResponse;
        }
    }

    @Override
    public UpdateImageResponseDto updateUserImage(Long userId, MultipartFile imageFile) {
        Optional<Users> usersOptional = usersRepository.findById(userId);
        String photoLink;
        if (usersOptional.isPresent()) {
            Users user = usersOptional.get();
            if (Objects.nonNull(user.getPhotoLink())) {
                String fileName = user.getPhotoLink().replaceAll(USER_IMAGES_FOLDER + "/", "");
                amazonS3Service.deleteFile(fileName, USER_IMAGES_FOLDER);
            }
            photoLink = amazonS3Service.uploadFile(imageFile, USER_IMAGES_FOLDER);
            user.setPhotoLink(photoLink);
            usersRepository.saveAndFlush(user);

            return new UpdateImageResponseDto(photoLink);
        }
        throw new ValidationException("User is not found", ErrorCode.USER_NOT_FOUND);
    }

    private void validatePassword(ChangePasswordDto passwordDto, Optional<Users> currentUser) throws ValidationException {
        if (currentUser.isEmpty()) {
            throw new ValidationException("The user was not found by this email", ErrorCode.USER_NOT_FOUND);
        }

        if (Objects.isNull(passwordDto.getPreviousPassword())
                || Objects.isNull(passwordDto.getNewPassword())
                || Objects.isNull(passwordDto.getCheckNewPassword())) {
            throw new ValidationException("One of the fields are empty", ErrorCode.FIELD_IS_INVALID);
        }

        if (!passwordEncoder.matches(passwordDto.getPreviousPassword(), currentUser.get().getPassword())) {
            throw new ValidationException("The previous password is incorrect", ErrorCode.PASSWORD_IS_NOT_CORRECT);
        }

        if (!Objects.equals(passwordDto.getNewPassword(), passwordDto.getCheckNewPassword())) {
            throw new ValidationException("The new passwords don't match", ErrorCode.PASSWORD_IS_NOT_CORRECT);
        }
    }
}
