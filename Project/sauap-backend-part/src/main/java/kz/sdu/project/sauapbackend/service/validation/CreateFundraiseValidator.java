package kz.sdu.project.sauapbackend.service.validation;

import kz.sdu.project.sauapbackend.dto.request.CreateFundraiseRequestDto;
import kz.sdu.project.sauapbackend.exception.ErrorCode;
import kz.sdu.project.sauapbackend.exception.ValidationException;
import kz.sdu.project.sauapbackend.service.ValidatorService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@Qualifier("createFundraiseValidator")
public class CreateFundraiseValidator implements ValidatorService<CreateFundraiseRequestDto> {

    @Override
    public void validate(CreateFundraiseRequestDto object) throws ValidationException {
        try {
            Objects.requireNonNull(object.getFundraiserTitle(), "'name' field is empty");
            Objects.requireNonNull(object.getGoalAmount(), "'goalAmount' field is empty");
            Objects.requireNonNull(object.getShortTitle(), "'shortTitle' field is empty");
            Objects.requireNonNull(object.getDescription(), "'getDescription' field is empty");
        } catch (NullPointerException ex) {
            throw new ValidationException(ex.getMessage(), ErrorCode.FIELD_IS_INVALID);
        }
    }

}
