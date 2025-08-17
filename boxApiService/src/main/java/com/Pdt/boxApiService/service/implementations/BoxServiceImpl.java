package com.Pdt.boxApiService.service.implementations;

import com.Pdt.boxApiService.dtos.request.BoxRequest;
import com.Pdt.boxApiService.dtos.request.ItemRequest;
import com.Pdt.boxApiService.dtos.response.BoxResponse;
import com.Pdt.boxApiService.dtos.response.ItemResponse;
import com.Pdt.boxApiService.model.entity.Box;
import com.Pdt.boxApiService.model.enums.State;
import com.Pdt.boxApiService.repository.BoxRepository;
import com.Pdt.boxApiService.service.interfaces.BoxService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
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
        Box newBox = new Box();
        newBox.setTxref(request.txref());
        newBox.setWeightLimit(request.weightLimit());
        newBox.setBatteryCapacity(request.batteryCapacity());
        newBox.setState(State.IDLE);
        newBox = boxRepository.save(newBox);
        return mapToBoxResponse(newBox);
    }

    @Override
    public void loadBoxWithItems(Long boxId, List<ItemRequest> itemRequests) {

    }

    @Override
    public List<ItemResponse> getLoadedItems(Long boxId) {
        return List.of();
    }

    @Override
    public List<BoxResponse> getAvailableBoxes() {
        return List.of();
    }

    @Override
    public int getBatteryLevel(Long boxId) {
        return 0;
    }

    private BoxResponse mapToBoxResponse(Box box) {
        List<ItemResponse> items = box.getItems().stream()
                .map(item -> new ItemResponse(item.getId(), item.getName(), item.getWeight(), item.getCode()))
                .collect(Collectors.toList());
        return new BoxResponse(box.getId(), box.getTxref(), box.getWeightLimit(), box.getBatteryCapacity(), box.getState(), items);
    }
}
