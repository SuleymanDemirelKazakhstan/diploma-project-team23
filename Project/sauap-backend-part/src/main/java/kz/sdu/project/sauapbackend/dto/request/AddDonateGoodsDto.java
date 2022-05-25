package kz.sdu.project.sauapbackend.dto.request;

import kz.sdu.project.sauapbackend.entity.model.Schedule;
import lombok.Data;

@Data
public class AddDonateGoodsDto {
    private String city;
    private String type;
    private String address;
    private String building;
    private String phoneNumber;
    private String responsiblePerson;
    private String conditions;
    private Location location;
    private ScheduleDto schedule;

    @Data
    public static class Location {
        private String latitude;
        private String longitude;
    }

    @Data
    public static class ScheduleDto {
        private Schedule monday;
        private Schedule tuesday;
        private Schedule wednesday;
        private Schedule thursday;
        private Schedule friday;
        private Schedule saturday;
        private Schedule sunday;
    }
}
