package com.Pdt.boxApiService.controller;

import com.Pdt.boxApiService.dtos.request.BoxRequest;
import com.Pdt.boxApiService.dtos.response.BoxResponse;
import com.Pdt.boxApiService.dtos.request.ItemRequest;
import com.Pdt.boxApiService.dtos.response.ItemResponse;
import com.Pdt.boxApiService.exception.InvalidBoxDataException;
import com.Pdt.boxApiService.service.interfaces.BoxService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/boxes")
public class BoxController {

    private final BoxService boxService;

    public BoxController(BoxService boxService) {
        this.boxService = boxService;
    }

    @PostMapping
    public ResponseEntity<BoxResponse> createBox(@Valid @RequestBody BoxRequest request) throws InvalidBoxDataException {
        return ResponseEntity.ok(boxService.createBox(request));
    }

    @PostMapping("/{boxId}/items")
    public ResponseEntity<Void> loadBoxWithItems(@PathVariable Long boxId, @Valid @RequestBody List<ItemRequest> itemRequests) {
        boxService.loadBoxWithItems(boxId, itemRequests);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{boxId}/items")
    public ResponseEntity<List<ItemResponse>> getLoadedItems(@PathVariable Long boxId) {
        return ResponseEntity.ok(boxService.getLoadedItems(boxId));
    }

    @GetMapping("/available")
    public ResponseEntity<List<BoxResponse>> getAvailableBoxes() {
        return ResponseEntity.ok(boxService.getAvailableBoxes());
    }

    @GetMapping("/{boxId}/battery")
    public ResponseEntity<Integer> getBatteryLevel(@PathVariable Long boxId) {
        return ResponseEntity.ok(boxService.getBatteryLevel(boxId));
    }
}