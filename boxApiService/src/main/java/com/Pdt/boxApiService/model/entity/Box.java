package com.Pdt.boxApiService.model.entity;

import com.Pdt.boxApiService.model.enums.State;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Box {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 20, message = "txref must be at most 20 characters")
    private String txref;

    @Max(value = 500, message = "weightLimit must be at most 500g")
    private double weightLimit;

    @Min(value = 0, message = "batteryCapacity must be at least 0")
    @Max(value = 100, message = "batteryCapacity must be at most 100")
    private int batteryCapacity;

    @Enumerated(EnumType.STRING)
    private State state = State.IDLE;

    @OneToMany(mappedBy = "box", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Item> items = new ArrayList<>();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTxref() { return txref; }
    public void setTxref(String txref) { this.txref = txref; }

    public double getWeightLimit() { return weightLimit; }
    public void setWeightLimit(double weightLimit) { this.weightLimit = weightLimit; }

    public int getBatteryCapacity() { return batteryCapacity; }
    public void setBatteryCapacity(int batteryCapacity) { this.batteryCapacity = batteryCapacity; }

    public State getState() { return state; }
    public void setState(State state) { this.state = state; }

    public List<Item> getItems() { return items; }
    public void setItems(List<Item> items) { this.items = items; }
}