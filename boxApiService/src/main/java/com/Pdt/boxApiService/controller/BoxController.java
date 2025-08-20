package com.Pdt.boxApiService.controller;

import com.Pdt.boxApiService.dtos.request.BoxRequest;
import com.Pdt.boxApiService.dtos.response.BoxResponse;
import com.Pdt.boxApiService.dtos.request.ItemRequest;
import com.Pdt.boxApiService.dtos.response.ItemResponse;
import com.Pdt.boxApiService.exception.InvalidBoxDataException;
import com.Pdt.boxApiService.service.interfaces.BoxService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/boxes")
@Tag(name = "Box API", description = "API for managing delivery boxes")
public class BoxController {

    private final BoxService boxService;

    public BoxController(BoxService boxService) {
        this.boxService = boxService;
    }

    @PostMapping
    @Operation(summary = "Create a new box", responses = {
            @ApiResponse(responseCode = "200", description = "Box created")
    })
    public ResponseEntity<BoxResponse> createBox(@Valid @RequestBody BoxRequest request) throws InvalidBoxDataException {
        return ResponseEntity.ok(boxService.createBox(request));
    }

    @PostMapping("/{boxId}/items")
    @Operation(summary = "Load items into a box", responses = {
            @ApiResponse(responseCode = "200", description = "Items loaded")
    })
    public ResponseEntity<Void> loadBoxWithItems(@PathVariable Long boxId, @Valid @RequestBody List<ItemRequest> itemRequests) {
        boxService.loadBoxWithItems(boxId, itemRequests);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{boxId}/items")
    @Operation(summary = "Get loaded items for a box", responses = {
            @ApiResponse(responseCode = "200", description = "Items returned")
    })
    public ResponseEntity<List<ItemResponse>> getLoadedItems(@PathVariable Long boxId) {
        return ResponseEntity.ok(boxService.getLoadedItems(boxId));
    }

    @GetMapping("/available")
    @Operation(summary = "Get available boxes for loading", responses = {
            @ApiResponse(responseCode = "200", description = "Available boxes returned")
    })
    public ResponseEntity<List<BoxResponse>> getAvailableBoxes() {
        return ResponseEntity.ok(boxService.getAvailableBoxes());
    }

    @GetMapping("/{boxId}/battery")
    @Operation(summary = "Get battery level for a box", responses = {
            @ApiResponse(responseCode = "200", description = "Battery level returned")
    })
    public ResponseEntity<Integer> getBatteryLevel(@PathVariable Long boxId) {
        return ResponseEntity.ok(boxService.getBatteryLevel(boxId));
    }
}