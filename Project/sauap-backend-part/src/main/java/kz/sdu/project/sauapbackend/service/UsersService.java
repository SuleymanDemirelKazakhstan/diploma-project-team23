package kz.sdu.project.sauapbackend.service;

import kz.sdu.project.sauapbackend.dto.UserDto;
import kz.sdu.project.sauapbackend.dto.request.ChangePasswordDto;
import kz.sdu.project.sauapbackend.dto.request.UpdateUserDto;
import kz.sdu.project.sauapbackend.dto.response.CheckPasswordResponseDto;
import kz.sdu.project.sauapbackend.dto.response.ServerDefaultResponse;
import kz.sdu.project.sauapbackend.dto.response.UpdateImageResponseDto;
import kz.sdu.project.sauapbackend.dto.response.UserInfoDto;
import kz.sdu.project.sauapbackend.exception.ValidationException;
import org.springframework.web.multipart.MultipartFile;

public interface UsersService {

    ServerDefaultResponse registerNewAccount(UserDto request);

    UserInfoDto getUserByEmail(String email);

    void updateUser(UpdateUserDto userDto, String email);

    CheckPasswordResponseDto checkUserPassword(String email, String password) throws ValidationException;

    ServerDefaultResponse changeUserPassword(ChangePasswordDto passwordDto);

    UpdateImageResponseDto updateUserImage(Long userId, MultipartFile imageFile);

}
