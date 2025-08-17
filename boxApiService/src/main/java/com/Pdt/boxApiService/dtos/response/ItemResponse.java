package com.Pdt.boxApiService.dtos.response;

public record ItemResponse(Long id,
                           String name,
                           double weight, String code) {}