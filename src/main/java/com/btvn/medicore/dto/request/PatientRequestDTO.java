package com.btvn.medicore.dto.request;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PatientRequestDTO {

    @NotBlank
    private String fullName;

    private String gender;

    private String phone;

    private String address;

    private String medicalHistory;
}