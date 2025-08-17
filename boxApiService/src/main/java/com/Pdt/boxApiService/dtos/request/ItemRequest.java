package com.Pdt.boxApiService.dtos.request;

import jakarta.validation.constraints.Pattern;

public record ItemRequest(
        @Pattern(regexp = "^[a-zA-Z0-9_-]+$")
        String name,
        double weight,
        @Pattern(regexp = "^[A-Z0-9_]+$")
        String code
) {}