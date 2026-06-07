package com.btvn.medicore.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DoctorResponseDTO {

    private Long id;

    private String fullName;

    private String specialization;

    private String phone;

    private String email;

    private String roomNumber;
}
