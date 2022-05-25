package kz.sdu.project.sauapbackend.mapper;

import kz.sdu.project.sauapbackend.dto.request.CreateFoundationDto;
import kz.sdu.project.sauapbackend.entity.FoundationCredits;
import org.modelmapper.ModelMapper;

public class FoundationCreditsMapper {

    private static final ModelMapper modelMapper = new ModelMapper();

    private FoundationCreditsMapper() { }

    public static FoundationCredits toEntity(CreateFoundationDto.FoundationCredits foundationCredits) {
        return modelMapper.map(foundationCredits, FoundationCredits.class);
    }

}
