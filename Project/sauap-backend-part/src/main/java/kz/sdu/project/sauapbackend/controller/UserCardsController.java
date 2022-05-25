package kz.sdu.project.sauapbackend.controller;

import kz.sdu.project.sauapbackend.dto.UserCardDto;
import kz.sdu.project.sauapbackend.dto.response.GetUserCardsResponseDto;
import kz.sdu.project.sauapbackend.service.UserCardsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user-cards")
public class UserCardsController {

    private final UserCardsService userCardsService;

    public UserCardsController(UserCardsService userCardsService) {
        this.userCardsService = userCardsService;
    }
    /**
     * User Card endpoints
     */
    @PostMapping(value = "/add", produces = "application/json", consumes = "application/json")
    public ResponseEntity<?> addNewCardToUser(@RequestParam("userId") Long userId,
                                              @RequestBody UserCardDto newUserCard) {
        return ResponseEntity.ok().body(userCardsService.addNewCard(newUserCard, userId));
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<?> deleteCard(@RequestParam("userId") Long userId,
                                        @RequestParam("cardId") Long cardId) {
        userCardsService.deleteCard(cardId, userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "", produces = "application/json")
    public ResponseEntity<?> getCardsOfUser(@RequestParam("userId") Long userId) {
        Optional<List<GetUserCardsResponseDto>> cardsOptional = userCardsService.getUserCardsByUserId(userId);
        if (cardsOptional.isEmpty()) {
            return ResponseEntity.ok().body(Collections.emptyList());
        }
        return ResponseEntity.ok().body(cardsOptional.get());
    }

}
