package kz.sdu.project.sauapbackend.service.impl;

import kz.sdu.project.sauapbackend.dto.DonateGoodsDto;
import kz.sdu.project.sauapbackend.dto.request.AddDonateGoodsDto;
import kz.sdu.project.sauapbackend.entity.PointOfCollection;
import kz.sdu.project.sauapbackend.entity.PointOfCollectionSchedule;
import kz.sdu.project.sauapbackend.exception.ErrorCode;
import kz.sdu.project.sauapbackend.exception.ValidationException;
import kz.sdu.project.sauapbackend.mapper.PointOfCollectionMapper;
import kz.sdu.project.sauapbackend.repository.PointOfCollectionRepository;
import kz.sdu.project.sauapbackend.repository.PointOfCollectionScheduleRepository;
import kz.sdu.project.sauapbackend.service.PointOfCollectionService;
import kz.sdu.project.sauapbackend.service.ValidatorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class PointOfCollectionServiceImpl implements PointOfCollectionService {

    private static final String UNDEFINED = "-";

    private final PointOfCollectionRepository pointOfCollectionRepository;
    private final PointOfCollectionScheduleRepository pointOfCollectionScheduleRepository;
    private final ValidatorService<AddDonateGoodsDto> validatorService;

    @Override
    public void addNewPointOfCollection(AddDonateGoodsDto donateGoodsDto) {
        validatorService.validate(donateGoodsDto);

        PointOfCollection pointOfCollection = PointOfCollectionMapper.toEntity(donateGoodsDto);
        pointOfCollection.setLatitude(Double.valueOf(donateGoodsDto.getLocation().getLatitude()));
        pointOfCollection.setLongitude(Double.valueOf(donateGoodsDto.getLocation().getLongitude()));
        pointOfCollection.setIsActive(true);

        var savedPointOfCollection = pointOfCollectionRepository.saveAndFlush(pointOfCollection);

        PointOfCollectionSchedule pointOfCollectionSchedule = PointOfCollectionMapper.toEntity(donateGoodsDto.getSchedule());
        pointOfCollectionSchedule.setCollectionId(savedPointOfCollection.getCollectionId());

        pointOfCollectionScheduleRepository.saveAndFlush(pointOfCollectionSchedule);
        log.info("POINT OF COLLECTION | NEW COLLECTION ADDED | {}", donateGoodsDto);
    }

    @Override
    public List<DonateGoodsDto> getListOfPointsOfCollection(String city) {
        List<PointOfCollection> pointOfCollections = pointOfCollectionRepository.findAllByCityAndIsActiveTrue(city);
        return getList(pointOfCollections);
    }

    @Override
    public List<DonateGoodsDto> getListOfPointsOfCollectionByType(String city, String type) {
        List<PointOfCollection> pointOfCollections = pointOfCollectionRepository.findAllByCityAndTypeAndIsActiveTrue(city, type);
        return getList(pointOfCollections);
    }

    @Override
    public DonateGoodsDto getPointOfCollectionById(Long collectionId) {
        Optional<PointOfCollection> pointOfCollection = pointOfCollectionRepository.findById(collectionId);
        if (pointOfCollection.isPresent()) {
            return exec(pointOfCollection.get());
        }
        throw new ValidationException(String.format("Donate good not found by id: %d", collectionId), ErrorCode.DONATE_GOOD_NOT_FOUND);
    }

    private List<DonateGoodsDto> getList(List<PointOfCollection> pointOfCollections) {
        if (Objects.isNull(pointOfCollections) || pointOfCollections.isEmpty()) {
            return Collections.emptyList();
        }

        List<DonateGoodsDto> resultList = new ArrayList<>();
        for (PointOfCollection pointOfCollection : pointOfCollections) {
            resultList.add(exec(pointOfCollection));
        }
        return resultList;
    }

    private DonateGoodsDto exec(PointOfCollection pointOfCollection) {
        DonateGoodsDto result = PointOfCollectionMapper.toDto(pointOfCollection);
        Optional<PointOfCollectionSchedule> schedules = pointOfCollectionScheduleRepository.findAllByCollectionId(pointOfCollection.getCollectionId());

        DonateGoodsDto.ScheduleDto scheduleDto = null;
        String openUntil = UNDEFINED;
        if (schedules.isPresent()) {
            scheduleDto = PointOfCollectionMapper.toDto(schedules.get());
            LocalDate today = LocalDate.now();
            DayOfWeek dayOfWeek = today.getDayOfWeek();
            int todayOfWeekValue = dayOfWeek.getValue();
            switch (todayOfWeekValue) {
                case 1:
                    if (scheduleDto.getMonday() != null) {
                        openUntil = scheduleDto.getMonday().getEnd();
                    }
                    break;
                case 2:
                    if (scheduleDto.getTuesday() != null) {
                        openUntil = scheduleDto.getTuesday().getEnd();
                    }
                    break;
                case 3:
                    if (scheduleDto.getWednesday() != null) {
                        openUntil = scheduleDto.getWednesday().getEnd();
                        break;
                    }
                case 4:
                    if (scheduleDto.getThursday() != null) {
                        openUntil = scheduleDto.getThursday().getEnd();
                    }
                    break;
                case 5:
                    if (scheduleDto.getFriday() != null) {
                        openUntil = scheduleDto.getFriday().getEnd();
                    }
                    break;
                case 6:
                    if (scheduleDto.getSaturday() != null) {
                        openUntil = scheduleDto.getSaturday().getEnd();
                    }
                    break;
                case 7:
                    if (scheduleDto.getSunday() != null) {
                        openUntil = scheduleDto.getSunday().getEnd();
                    }
                    break;
                default:
                    break;
            }
        }
        result.setSchedule(scheduleDto);
        result.setOpenUntil(openUntil);

        return result;
    }
}
