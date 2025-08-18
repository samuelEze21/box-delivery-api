package com.Pdt.boxApiService.service.interfaces;

import com.Pdt.boxApiService.dtos.request.BoxRequest;
import com.Pdt.boxApiService.dtos.response.BoxResponse;
import com.Pdt.boxApiService.dtos.request.ItemRequest;
import com.Pdt.boxApiService.dtos.response.ItemResponse;
import com.Pdt.boxApiService.exception.BoxRequestNullException;
import com.Pdt.boxApiService.exception.InvalidBoxDataException;

import java.util.List;

public interface BoxService {
    BoxResponse createBox(BoxRequest request) throws BoxRequestNullException, InvalidBoxDataException;
    void loadBoxWithItems(Long boxId, List<ItemRequest> itemRequests);
    List<ItemResponse> getLoadedItems(Long boxId);
    List<BoxResponse> getAvailableBoxes();
    int getBatteryLevel(Long boxId);
}