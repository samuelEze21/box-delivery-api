package com.Pdt.boxApiService.service.implementations;

import com.Pdt.boxApiService.dtos.request.BoxRequest;
import com.Pdt.boxApiService.dtos.request.ItemRequest;
import com.Pdt.boxApiService.dtos.response.BoxResponse;
import com.Pdt.boxApiService.dtos.response.ItemResponse;
import com.Pdt.boxApiService.service.interfaces.BoxService;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class BoxServiceImpl implements BoxService {


    @Override
    public BoxResponse createBox(BoxRequest request) {
        return null;
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
}
