package kz.sdu.project.sauapbackend.mapper;

import kz.sdu.project.sauapbackend.dto.FoundationDto;
import kz.sdu.project.sauapbackend.dto.response.FoundationDetailsResponseDto;
import kz.sdu.project.sauapbackend.entity.Foundation;
import kz.sdu.project.sauapbackend.entity.FoundationHasUsers;
import org.modelmapper.ModelMapper;

public class FoundationMapper {
    private static final ModelMapper modelMapper = new ModelMapper();

    private FoundationMapper() { }

    public static FoundationDto toDto(Foundation foundation) {
        return modelMapper.map(foundation, FoundationDto.class);
    }

    public static FoundationDetailsResponseDto toDetailedDto(Foundation foundation) {
        return modelMapper.map(foundation, FoundationDetailsResponseDto.class);
    }

    public static FoundationDetailsResponseDto.FoundationHasUsers toDto(FoundationHasUsers foundationHasUsers) {
        return modelMapper.map(foundationHasUsers, FoundationDetailsResponseDto.FoundationHasUsers.class);
    }
}
