package kz.sdu.project.sauapbackend.mapper;

import kz.sdu.project.sauapbackend.dto.FundraiseDto;
import kz.sdu.project.sauapbackend.dto.response.FundraiseDetailResponseDto;
import kz.sdu.project.sauapbackend.entity.Fundraising;
import org.modelmapper.ModelMapper;

public class FundraiseMapper {
    private static final ModelMapper modelMapper = new ModelMapper();

    private FundraiseMapper() {}

    public static FundraiseDto toDto(Fundraising fundraising) {
        return modelMapper.map(fundraising, FundraiseDto.class);
    }

    public static FundraiseDetailResponseDto toDetailDto(Fundraising fundraising) {
        return modelMapper.map(fundraising, FundraiseDetailResponseDto.class);
    }
}
