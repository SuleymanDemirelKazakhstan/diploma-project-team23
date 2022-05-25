package kz.sdu.project.sauapbackend.controller;

import kz.sdu.project.sauapbackend.dto.request.AddDonateGoodsDto;
import kz.sdu.project.sauapbackend.service.PointOfCollectionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/donate-goods")
public class PointOfCollectionController {

    private final PointOfCollectionService pointOfCollectionService;

    public PointOfCollectionController(PointOfCollectionService pointOfCollectionService) {
        this.pointOfCollectionService = pointOfCollectionService;
    }

    @PostMapping(value = "/add",
            produces = "application/json",
            consumes = "application/json")
    public ResponseEntity<?> addNewPoints(@RequestBody AddDonateGoodsDto addDonateGoodsDto) {
        pointOfCollectionService.addNewPointOfCollection(addDonateGoodsDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/list", produces = "application/json")
    public ResponseEntity<?> getListOfPoints(@RequestParam("city") String city,
                                             @RequestParam(value = "type", required = false) String type) {
        if (Objects.isNull(type))
            return ResponseEntity.ok(pointOfCollectionService.getListOfPointsOfCollection(city));
        return ResponseEntity.ok(pointOfCollectionService.getListOfPointsOfCollectionByType(city, type));
    }

    @GetMapping(value = "/by-id", produces = "application/json")
    public ResponseEntity<?> getDonateGoodsById(@RequestParam("id") Long collectionId) {
        return ResponseEntity.ok().body(pointOfCollectionService.getPointOfCollectionById(collectionId));
    }

}
