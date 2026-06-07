package com.btvn.medicore.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppointmentRequestDTO {

    @NotNull
    private Long doctorId;

    @NotNull
    private LocalDateTime appointmentTime;
}