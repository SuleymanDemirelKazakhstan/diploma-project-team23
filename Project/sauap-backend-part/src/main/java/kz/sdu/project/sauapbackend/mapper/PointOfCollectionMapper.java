package kz.sdu.project.sauapbackend.mapper;

import kz.sdu.project.sauapbackend.dto.DonateGoodsDto;
import kz.sdu.project.sauapbackend.dto.request.AddDonateGoodsDto;
import kz.sdu.project.sauapbackend.entity.PointOfCollection;
import kz.sdu.project.sauapbackend.entity.PointOfCollectionSchedule;
import org.modelmapper.ModelMapper;

public class PointOfCollectionMapper {

    private static final ModelMapper modelMapper = new ModelMapper();

    private PointOfCollectionMapper() {}

    public static DonateGoodsDto toDto(PointOfCollection entity) {
        DonateGoodsDto dto = modelMapper.map(entity, DonateGoodsDto.class);
        DonateGoodsDto.Location location = new DonateGoodsDto.Location();
        location.setLongitude(String.valueOf(entity.getLongitude()));
        location.setLatitude(String.valueOf(entity.getLatitude()));
        dto.setLocation(location);
        return dto;
    }

    public static DonateGoodsDto.ScheduleDto toDto(PointOfCollectionSchedule entity) {
        return modelMapper.map(entity, DonateGoodsDto.ScheduleDto.class);
    }

    public static PointOfCollection toEntity(AddDonateGoodsDto dto) {
        return modelMapper.map(dto, PointOfCollection.class);
    }

    public static PointOfCollectionSchedule toEntity(AddDonateGoodsDto.ScheduleDto dto) {
        return modelMapper.map(dto, PointOfCollectionSchedule.class);
    }

}
