package com.Pdt.boxApiService.dtos.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;


public record BoxRequest(
        @Size(max = 20) String txref,
        @Max(500) double weightLimit,
        @Min(0) @Max(100) int batteryCapacity
) {}