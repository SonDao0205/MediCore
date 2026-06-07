package com.btvn.medicore.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class AppointmentResponseDTO {

    private Long id;

    private String doctorName;

    private String patientName;

    private LocalDateTime appointmentTime;

    private String status;
}