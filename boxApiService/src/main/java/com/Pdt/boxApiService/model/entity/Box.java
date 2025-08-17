package com.Pdt.boxApiService.model.entity;

import com.Pdt.boxApiService.model.enums.State;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Box {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 20, message = "txref must be at most 20 characters")
    private String txref;

    @Max(value = 500, message = "weightLimit must be at most 500")
    private double weightLimit;

    @Min(value = 0, message = "batteryCapacity must be at least 0")
    @Max(value = 100, message = "batteryCapacity must be at most 100")
    private int batteryCapacity;

    @Enumerated(EnumType.STRING)
    private State state = State.IDLE;

    @OneToMany(mappedBy = "box", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Item> items = new ArrayList<>();
}