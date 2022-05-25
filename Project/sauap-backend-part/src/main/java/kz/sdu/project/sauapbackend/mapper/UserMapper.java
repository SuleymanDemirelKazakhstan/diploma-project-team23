package kz.sdu.project.sauapbackend.mapper;

import kz.sdu.project.sauapbackend.dto.UserCardDto;
import kz.sdu.project.sauapbackend.dto.UserDto;
import kz.sdu.project.sauapbackend.dto.request.UpdateUserDto;
import kz.sdu.project.sauapbackend.dto.response.UserInfoDto;
import kz.sdu.project.sauapbackend.entity.UserCards;
import kz.sdu.project.sauapbackend.entity.Users;
import org.modelmapper.ModelMapper;

public class UserMapper {
    private static final ModelMapper modelMapper = new ModelMapper();

    private UserMapper() {}

    public static void toEntity(UpdateUserDto userDto, Users users) {
        modelMapper.map(userDto, users);
    }

    public static Users toEntity(UserDto userDto) {
        Users users = new Users();
        if (userDto.getPhoneNumber() != null) {
            users.setPhoneNumber(userDto.getPhoneNumber());
        }
        if (userDto.getFirstName() != null) {
            users.setFirstName(userDto.getFirstName());
        }
        if (userDto.getLastName() != null) {
            users.setLastname(userDto.getLastName());
        }
        if (userDto.getGender() != null) {
            users.setGender(userDto.getGender());
        }
        if (userDto.getPassword() != null) {
            users.setPassword(userDto.getPassword());
        }
        if (userDto.getIsActive() != null) {
            users.setIsActive(userDto.getIsActive());
        }
        if (userDto.getEmail() != null) {
            users.setEmail(userDto.getEmail());
        }
        if (userDto.getAddress() != null) {
            users.setAddress(userDto.getAddress());
        }
        if (userDto.getCity() != null) {
            users.setCity(userDto.getCity());
        }
        if (userDto.getIin() != null) {
            users.setIin(userDto.getIin());
        }
        if (userDto.getBirthDate() != null) {
            users.setBirthDate(userDto.getBirthDate());
        }
        return users;
    }

    public static UserInfoDto toDto(Users users) {
        return modelMapper.map(users, UserInfoDto.class);
    }

    public static UserCards toEntity(UserCardDto dto) {
        return modelMapper.map(dto, UserCards.class);
    }
}
