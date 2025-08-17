package com.Pdt.boxApiService.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Entity
@Data
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Pattern(regexp = "^[a-zA-Z0-9_-]+$", message = "name can only contain letters, numbers, '-', '_'")
    private String name;

    private double weight;

    @Pattern(regexp = "^[A-Z0-9_]+$", message = "code can only contain uppercase letters, '_', numbers")
    private String code;

    @ManyToOne
    @JoinColumn(name = "box_id")
    private Box box;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @Pattern(regexp = "^[a-zA-Z0-9_-]+$", message = "name can only contain letters, numbers, '-', '_'") String getName() {
        return name;
    }

    public void setName(@Pattern(regexp = "^[a-zA-Z0-9_-]+$", message = "name can only contain letters, numbers, '-', '_'") String name) {
        this.name = name;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public @Pattern(regexp = "^[A-Z0-9_]+$", message = "code can only contain uppercase letters, '_', numbers") String getCode() {
        return code;
    }

    public void setCode(@Pattern(regexp = "^[A-Z0-9_]+$", message = "code can only contain uppercase letters, '_', numbers") String code) {
        this.code = code;
    }

    public Box getBox() {
        return box;
    }

    public void setBox(Box box) {
        this.box = box;
    }
}