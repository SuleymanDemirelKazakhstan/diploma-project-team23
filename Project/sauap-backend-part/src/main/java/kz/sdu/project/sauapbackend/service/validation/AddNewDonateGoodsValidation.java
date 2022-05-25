package kz.sdu.project.sauapbackend.service.validation;

import kz.sdu.project.sauapbackend.dto.request.AddDonateGoodsDto;
import kz.sdu.project.sauapbackend.exception.ErrorCode;
import kz.sdu.project.sauapbackend.exception.ValidationException;
import kz.sdu.project.sauapbackend.service.ValidatorService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@Qualifier("AddNewDonateGoodsValidation")
public class AddNewDonateGoodsValidation implements ValidatorService<AddDonateGoodsDto> {

    @Override
    public void validate(AddDonateGoodsDto object) throws ValidationException {
        try {
            Objects.requireNonNull(object.getCity(), "'city' field is empty");
            Objects.requireNonNull(object.getType(), "'type' field is empty");
            Objects.requireNonNull(object.getAddress(), "'address' field is empty");
            Objects.requireNonNull(object.getBuilding(), "'building' field is empty");
            Objects.requireNonNull(object.getPhoneNumber(), "'phoneNumber' field is empty");
            Objects.requireNonNull(object.getResponsiblePerson(), "'responsiblePerson' field is empty");
            Objects.requireNonNull(object.getConditions(), "'conditions' field is empty");

            AddDonateGoodsDto.Location location = object.getLocation();
            Objects.requireNonNull(location.getLatitude(), "'location.latitude' field is empty");
            Objects.requireNonNull(location.getLongitude(), "'location.longitude' field is empty");

            Double.parseDouble(location.getLatitude());
            Double.parseDouble(location.getLongitude());
        } catch (NullPointerException | NumberFormatException ex) {
            throw new ValidationException(ex.getMessage(), ErrorCode.FIELD_IS_INVALID);
        }
    }
}
