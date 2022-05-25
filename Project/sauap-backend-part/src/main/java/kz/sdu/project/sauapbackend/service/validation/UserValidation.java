package kz.sdu.project.sauapbackend.service.validation;

import kz.sdu.project.sauapbackend.dto.UserDto;
import kz.sdu.project.sauapbackend.entity.Users;
import kz.sdu.project.sauapbackend.exception.ErrorCode;
import kz.sdu.project.sauapbackend.exception.ValidationException;
import kz.sdu.project.sauapbackend.repository.UsersRepository;
import kz.sdu.project.sauapbackend.service.ValidatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
@Qualifier("userValidator")
public class UserValidation implements ValidatorService<UserDto> {

    @Autowired
    public UsersRepository usersRepository;

    private static final String EMAIL_REGEX = "^(.+)@(.+)$";

    @Override
    public void validate(UserDto request) throws ValidationException {
        // check for NullPointer exception
        if (Objects.isNull(request.getEmail())) {
            throw new ValidationException("{email} field is empty", ErrorCode.FIELD_IS_INVALID);
        }
        if (Objects.isNull(request.getPassword())) {
            throw new ValidationException("{password} field is empty", ErrorCode.FIELD_IS_INVALID);
        }
        if (Objects.isNull(request.getFirstName())) {
            throw new ValidationException("{first_name} field is empty", ErrorCode.FIELD_IS_INVALID);
        }
        if (!request.getEmail().matches(EMAIL_REGEX)) {
            throw new ValidationException("{email} format is incorrect", ErrorCode.FIELD_FORMAT_INVALID);
        }

        Optional<Users> usersOptional = usersRepository.findByEmail(request.getEmail());
        if (usersOptional.isPresent()) {
            throw new ValidationException("Email is already exist", ErrorCode.USER_ALREADY_EXIST);
        }
    }
}
