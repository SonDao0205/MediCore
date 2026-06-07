package com.btvn.medicore.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MedicineResponseDTO {

    private Long id;

    private String name;

    private String description;

    private Double price;

    private Integer stock;
}