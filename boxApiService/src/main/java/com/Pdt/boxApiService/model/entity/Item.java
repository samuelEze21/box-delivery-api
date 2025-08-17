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
}