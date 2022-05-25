package kz.sdu.project.sauapbackend.mapper;

import kz.sdu.project.sauapbackend.dto.RequestToFoundationDto;
import kz.sdu.project.sauapbackend.entity.RequestToFoundation;
import org.modelmapper.ModelMapper;

public class RequestToFoundationMapper {

    private static final ModelMapper modelMapper = new ModelMapper();

    private RequestToFoundationMapper() {}

    public static RequestToFoundation toEntity(RequestToFoundationDto dto) {
        return modelMapper.map(dto, RequestToFoundation.class);
    }

    public static RequestToFoundationDto toDto(RequestToFoundation entity) {
        return modelMapper.map(entity, RequestToFoundationDto.class);
    }
}
