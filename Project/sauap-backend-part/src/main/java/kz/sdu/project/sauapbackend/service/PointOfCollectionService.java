package kz.sdu.project.sauapbackend.service;

import kz.sdu.project.sauapbackend.dto.DonateGoodsDto;
import kz.sdu.project.sauapbackend.dto.request.AddDonateGoodsDto;

import java.util.List;

public interface PointOfCollectionService {

    void addNewPointOfCollection(AddDonateGoodsDto donateGoodsDto);

    List<DonateGoodsDto> getListOfPointsOfCollection(String city);

    List<DonateGoodsDto> getListOfPointsOfCollectionByType(String city, String type);

    DonateGoodsDto getPointOfCollectionById(Long collectionId);

}
