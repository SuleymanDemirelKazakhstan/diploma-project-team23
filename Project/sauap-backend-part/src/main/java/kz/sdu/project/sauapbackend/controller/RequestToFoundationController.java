package kz.sdu.project.sauapbackend.controller;

import kz.sdu.project.sauapbackend.dto.RequestToFoundationDto;
import kz.sdu.project.sauapbackend.service.RequestsToFoundationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/help")
@RequiredArgsConstructor
public class RequestToFoundationController {

    private final RequestsToFoundationService requestsToFoundationService;

    @PostMapping(value = "/request",
            produces = "application/json",
            consumes = "application/json")
    public ResponseEntity<?> receiveNewRequest(@RequestBody RequestToFoundationDto request) {
        return ResponseEntity.ok().body(requestsToFoundationService.newRequest(request));
    }

    @GetMapping(value = "/by-foundation",
            produces = "application/json")
    public ResponseEntity<?> getRequestsByFoundation(@RequestParam("foundationId") Long foundationId) {
        return ResponseEntity.ok().body(requestsToFoundationService.getRequestsByFoundationId(foundationId));
    }

}
