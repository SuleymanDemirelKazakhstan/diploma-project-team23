package kz.sdu.project.sauapbackend.controller;

import kz.sdu.project.sauapbackend.dto.request.CreateFundraiseRequestDto;
import kz.sdu.project.sauapbackend.dto.request.PaymentRequestDto;
import kz.sdu.project.sauapbackend.dto.response.MainCreateResponse;
import kz.sdu.project.sauapbackend.service.FundraiseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/fundraise")
public class FundraisingController {

    private final FundraiseService fundraiseService;

    public FundraisingController(FundraiseService fundraiseService) {
        this.fundraiseService = fundraiseService;
    }

    @GetMapping(value = "/", produces = "application/json")
    public ResponseEntity<?> getFundraises(@RequestParam("is-completed")  boolean isCompleted) {
        return ResponseEntity.ok().body(fundraiseService.getAllFundraises(isCompleted));
    }

    @GetMapping(value = "/by-foundation", produces = "application/json")
    public ResponseEntity<?> getFundraiseByFoundation(@RequestParam("foundationId") Long foundationId) {
        return ResponseEntity.ok().body(fundraiseService.getFundraiseByFoundationId(foundationId));
    }

    @GetMapping(value = "/by-id", produces = "application/json")
    public ResponseEntity<?> getFundraiseById(@RequestParam("fundraiseId") Long fundraiseId) {
        return ResponseEntity.ok().body(fundraiseService.getFundraiseById(fundraiseId));
    }

    @PostMapping(value = "/create")
    public ResponseEntity<?> createFundraise(CreateFundraiseRequestDto createFundraiseRequestDto,
                                             @RequestParam("document") MultipartFile documentFile,
                                             @RequestParam("photo") MultipartFile imageFile,
                                             @RequestParam("foundationId") Long foundationId) {
        MainCreateResponse createFundraiseResponseDto = fundraiseService.createFundraise(
                createFundraiseRequestDto,
                documentFile,
                imageFile,
                foundationId);

        return ResponseEntity.ok().body(createFundraiseResponseDto);
    }

    @PostMapping(value = "/donate",
            produces = "application/json",
            consumes = "application/json")
    public ResponseEntity<?> donateToFundraise(@RequestBody PaymentRequestDto paymentRequestDto) {
        return ResponseEntity.ok().body(fundraiseService.donateToFundraise(paymentRequestDto));
    }
}
