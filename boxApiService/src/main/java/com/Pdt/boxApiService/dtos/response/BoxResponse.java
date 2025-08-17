package com.Pdt.boxApiService.dtos.response;

import com.Pdt.boxApiService.model.enums.State;

import java.util.List;

public record BoxResponse(Long id, String txref, double weightLimit,
                          int batteryCapacity,
                          State state,
                          List<ItemResponse> items) {}