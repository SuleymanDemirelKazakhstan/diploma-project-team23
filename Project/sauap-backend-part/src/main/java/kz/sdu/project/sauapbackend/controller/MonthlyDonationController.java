package kz.sdu.project.sauapbackend.controller;

import kz.sdu.project.sauapbackend.dto.request.AddNewMonthlyDonationRequestDto;
import kz.sdu.project.sauapbackend.dto.response.GetUserMonthDonationResponseDto;
import kz.sdu.project.sauapbackend.service.UserMonthlyDonateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/monthly-donation")
public class MonthlyDonationController {

    private final UserMonthlyDonateService userMonthlyDonateService;

    public MonthlyDonationController(UserMonthlyDonateService userMonthlyDonateService) {
        this.userMonthlyDonateService = userMonthlyDonateService;
    }

    /**
     * User Monthly Donation endpoints
     */
    @PostMapping(value = "/add", produces = "application/json", consumes = "application/json")
    public ResponseEntity<?> addNewMonthlyDonation(@RequestBody AddNewMonthlyDonationRequestDto request) {
        return ResponseEntity.ok().body(userMonthlyDonateService.addNewMonthlyDonation(request));
    }

    @GetMapping(value = "", produces = "application/json")
    public ResponseEntity<?> getMonthlyDonationList(@RequestParam("userId") Long userId) {
        Optional<List<GetUserMonthDonationResponseDto>> result = userMonthlyDonateService.getUserDonationList(userId);
        if (result.isEmpty()) {
            return ResponseEntity.ok().body(Collections.emptyList());
        }
        return ResponseEntity.ok().body(result.get());
    }
}
