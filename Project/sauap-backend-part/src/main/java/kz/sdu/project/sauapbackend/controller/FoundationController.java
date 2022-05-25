package kz.sdu.project.sauapbackend.controller;

import kz.sdu.project.sauapbackend.dto.FoundationDto;
import kz.sdu.project.sauapbackend.dto.request.CreateFoundationDto;
import kz.sdu.project.sauapbackend.dto.request.UpdateFoundationRequestDto;
import kz.sdu.project.sauapbackend.dto.response.MainCreateResponse;
import kz.sdu.project.sauapbackend.service.FoundationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/foundations")
@Slf4j
public class FoundationController {

    private final FoundationService foundationService;

    public FoundationController(FoundationService foundationService) {
        this.foundationService = foundationService;
    }

    @GetMapping(value = "/", produces = "application/json")
    public ResponseEntity<?> getAllFoundations() {
        Optional<List<FoundationDto>> allFoundations = foundationService.getAllFoundations();
        if (allFoundations.isEmpty()) {
            return ResponseEntity.ok().body(Collections.emptyList());
        }
        return ResponseEntity.ok().body(allFoundations.get());
    }

    @GetMapping(value = "/by-user", produces = "application/json")
    public ResponseEntity<?> getUserFoundations(@RequestParam("userId") Long userId) {
        return ResponseEntity.ok().body(foundationService.getUserFoundations(userId));
    }

    @GetMapping(value = "/by-id", produces = "application/json")
    public ResponseEntity<?> getFoundationInfoById(@RequestParam("foundationId") Long id) {
        FoundationDto foundationDto = foundationService.getFoundationById(id);
        return ResponseEntity.ok().body(foundationDto);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<?> createNewFoundation(CreateFoundationDto createFoundationDto,
                                                 @RequestParam("fileName") MultipartFile documentFile,
                                                 @RequestParam("userId") Long userId) {
        MainCreateResponse response = foundationService.requestToCreateNewFoundation(createFoundationDto, documentFile, userId);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping(value = "/uploadImage", produces = "application/json")
    public ResponseEntity<?> uploadFoundationImage(@RequestParam("foundationId") Long foundationId,
                                                   @RequestParam("image") MultipartFile imageFile) {
        return ResponseEntity.ok().body(foundationService.updateFoundationImage(foundationId, imageFile));
    }

    @PutMapping(value = "/update",
            produces = "application/json",
            consumes = "application/json")
    public ResponseEntity<?> update(@RequestParam("foundationId") Long foundationId,
                                    @RequestBody UpdateFoundationRequestDto request) {
        foundationService.update(request, foundationId);
        return ResponseEntity.ok().build();
    }
}
