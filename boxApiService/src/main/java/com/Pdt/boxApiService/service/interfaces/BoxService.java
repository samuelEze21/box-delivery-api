package com.Pdt.boxApiService.service.interfaces;

import com.Pdt.boxApiService.dtos.request.BoxRequest;
import com.Pdt.boxApiService.dtos.response.BoxResponse;
import com.Pdt.boxApiService.dtos.request.ItemRequest;
import com.Pdt.boxApiService.dtos.response.ItemResponse;
import java.util.List;

public interface BoxService {
    BoxResponse createBox(BoxRequest request);
    void loadBoxWithItems(Long boxId, List<ItemRequest> itemRequests);
    List<ItemResponse> getLoadedItems(Long boxId);
    List<BoxResponse> getAvailableBoxes();
    int getBatteryLevel(Long boxId);
}