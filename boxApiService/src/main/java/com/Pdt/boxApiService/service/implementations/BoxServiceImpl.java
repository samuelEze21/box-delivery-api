package com.Pdt.boxApiService.service.implementations;

import com.Pdt.boxApiService.dtos.request.BoxRequest;
import com.Pdt.boxApiService.dtos.response.BoxResponse;
import com.Pdt.boxApiService.dtos.request.ItemRequest;
import com.Pdt.boxApiService.dtos.response.ItemResponse;
import com.Pdt.boxApiService.exception.*;
import com.Pdt.boxApiService.model.entity.Box;
import com.Pdt.boxApiService.model.entity.Item;
import com.Pdt.boxApiService.model.enums.State;
import com.Pdt.boxApiService.repository.BoxRepository;
import com.Pdt.boxApiService.service.interfaces.BoxService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BoxServiceImpl implements BoxService {

    private final BoxRepository boxRepository;

    public BoxServiceImpl(BoxRepository boxRepository) {
        this.boxRepository = boxRepository;
    }

    @Override
    @Transactional
    public BoxResponse createBox(BoxRequest request) {
        if (request == null) {
            try {
                throw new BoxRequestNullException("Box request cannot be null");
            } catch (BoxRequestNullException e) {
                throw new RuntimeException(e);
            }
        }
        Box box = new Box();
        box.setTxref(request.txref());
        box.setWeightLimit(request.weightLimit());
        box.setBatteryCapacity(request.batteryCapacity());
        box.setState(State.IDLE);
        try {
            box = boxRepository.save(box);
            return mapToBoxResponse(box);
        } catch (IllegalArgumentException e) {
            try {
                throw new InvalidBoxDataException("Invalid box data: " + e.getMessage());
            } catch (InvalidBoxDataException ex) {
                throw new RuntimeException(ex);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to create box: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public void loadBoxWithItems(Long boxId, List<ItemRequest> itemRequests) {
        if (boxId == null) {
            throw new BoxIdNullException("Box ID cannot be null");
        }
        if (itemRequests == null || itemRequests.isEmpty()) {
            throw new ItemRequestsNullOrEmptyException("Item requests cannot be null or empty");
        }
        Box box = boxRepository.findById(boxId)
                .orElseThrow(() -> new BoxNotFoundException("Box not found with id: " + boxId));
        if (box.getState() != State.IDLE) {
            throw new BoxNotIdleStateException("Box must be in IDLE state to load");
        }
        if (box.getBatteryCapacity() < 25) {
            throw new LowBatteryException("Cannot load box with battery below 25%");
        }
        double currentWeight = box.getItems().stream().mapToDouble(Item::getWeight).sum();
        double newWeight = itemRequests.stream().mapToDouble(ItemRequest::weight).sum();
        if (currentWeight + newWeight > box.getWeightLimit()) {
            throw new WeightLimitExceededException("Total weight exceeds box limit");
        }
        for (ItemRequest req : itemRequests) {
            Item item = new Item();
            item.setName(req.name());
            item.setWeight(req.weight());
            item.setCode(req.code());
            item.setBox(box);
            box.getItems().add(item);
        }
        box.setState(State.LOADED);
        try {
            boxRepository.save(box);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load items into box: " + e.getMessage());
        }
    }

    @Override
    public List<ItemResponse> getLoadedItems(Long boxId) {
        if (boxId == null) {
            throw new BoxIdNullException("Box ID cannot be null");
        }
        Box box = boxRepository.findById(boxId)
                .orElseThrow(() -> new BoxNotFoundException("Box not found with id: " + boxId));
        return box.getItems().stream()
                .map(item -> new ItemResponse(item.getId(), item.getName(), item.getWeight(), item.getCode()))
                .collect(Collectors.toList());
    }

    @Override
    public List<BoxResponse> getAvailableBoxes() {
        List<Box> boxes = boxRepository.findByStateAndBatteryCapacityGreaterThanEqual(State.IDLE, 25);
        return boxes.stream().map(this::mapToBoxResponse).collect(Collectors.toList());
    }

    @Override
    public int getBatteryLevel(Long boxId) {
        if (boxId == null) {
            throw new BoxIdNullException("Box ID cannot be null");
        }
        Box box = boxRepository.findById(boxId)
                .orElseThrow(() -> new BoxNotFoundException("Box not found with id: " + boxId));
        return box.getBatteryCapacity();
    }

    private BoxResponse mapToBoxResponse(Box box) {
        List<ItemResponse> items = box.getItems().stream()
                .map(item -> new ItemResponse(item.getId(), item.getName(), item.getWeight(), item.getCode()))
                .collect(Collectors.toList());
        return new BoxResponse(box.getId(), box.getTxref(), box.getWeightLimit(), box.getBatteryCapacity(), box.getState(), items);
    }
}