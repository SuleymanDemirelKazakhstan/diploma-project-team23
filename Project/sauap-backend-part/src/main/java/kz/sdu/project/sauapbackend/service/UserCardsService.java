package kz.sdu.project.sauapbackend.service;

import kz.sdu.project.sauapbackend.dto.UserCardDto;
import kz.sdu.project.sauapbackend.dto.response.GetUserCardsResponseDto;
import kz.sdu.project.sauapbackend.dto.response.MainCreateResponse;

import java.util.List;
import java.util.Optional;

public interface UserCardsService {

    MainCreateResponse addNewCard(UserCardDto newUserCard, Long userId);

    void deleteCard(Long cardId, Long userId);

    Optional<List<GetUserCardsResponseDto>> getUserCardsByUserId(Long userId);

}
