package com.btvn.medicore.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class MedicineRequestDTO {

    @NotBlank
    private String name;

    private String description;

    @Positive
    private Double price;

    @PositiveOrZero
    private Integer stock;
}