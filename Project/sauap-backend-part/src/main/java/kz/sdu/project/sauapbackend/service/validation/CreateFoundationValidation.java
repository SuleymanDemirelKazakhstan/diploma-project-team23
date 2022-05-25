package kz.sdu.project.sauapbackend.service.validation;

import kz.sdu.project.sauapbackend.dto.request.CreateFoundationDto;
import kz.sdu.project.sauapbackend.exception.ErrorCode;
import kz.sdu.project.sauapbackend.exception.ValidationException;
import kz.sdu.project.sauapbackend.service.ValidatorService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@Qualifier("createFoundationValidation")
public class CreateFoundationValidation implements ValidatorService<CreateFoundationDto> {

    @Override
    public void validate(CreateFoundationDto createFoundationDto) throws ValidationException {
        try {
            Objects.requireNonNull(createFoundationDto.getFoundationName(), "'foundation_name' field is empty");
            Objects.requireNonNull(createFoundationDto.getBin(), "'bin' field is empty");
            Objects.requireNonNull(createFoundationDto.getCredits(), "'credits' field is empty");
            Objects.requireNonNull(createFoundationDto.getCity(), "'city' field is empty");
            Objects.requireNonNull(createFoundationDto.getContactName(), "'contactName' field is empty");
            Objects.requireNonNull(createFoundationDto.getPhoneNumber(), "'phoneNumber' field is empty");

            CreateFoundationDto.FoundationCredits foundationCredits = createFoundationDto.getCredits();
            Objects.requireNonNull(foundationCredits.getAccount(), "'credits.account' field is empty");
            Objects.requireNonNull(foundationCredits.getBic(), "'credits.bic' field is empty");
            Objects.requireNonNull(foundationCredits.getBin(), "'credits.bin' field is empty");
            Objects.requireNonNull(foundationCredits.getKbe(), "'credits.kbe' field is empty");
            Objects.requireNonNull(foundationCredits.getKnp(), "'credits.knp' field is empty");
            Objects.requireNonNull(foundationCredits.getLegalAddress(), "'credits.legalAddress' field is empty");
        } catch (NullPointerException ex) {
            throw new ValidationException(ex.getMessage(), ErrorCode.FIELD_IS_INVALID);
        }
    }

}
