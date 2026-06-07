package com.btvn.medicore.controller;

import com.btvn.medicore.dto.request.AppointmentRequestDTO;
import com.btvn.medicore.dto.response.ApiDataResponse;
import com.btvn.medicore.dto.response.AppointmentResponseDTO;
import com.btvn.medicore.service.AppointmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PostMapping
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<ApiDataResponse<AppointmentResponseDTO>> createAppointment(
            @Valid @RequestBody AppointmentRequestDTO dto
    ) {
        return new ResponseEntity<>(new ApiDataResponse<>(
                true,
                "Thêm Appointment thành công!",
                appointmentService.createAppointment(dto),
                HttpStatus.CREATED
        ), HttpStatus.CREATED);
    }

    @GetMapping("/my")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<ApiDataResponse<List<AppointmentResponseDTO>>> myAppointments() {
        return new ResponseEntity<>(new ApiDataResponse<>(
                true,
                "Lấy danh sách lịch hẹn của tôi thành công!",
                appointmentService.myAppointments(),
                HttpStatus.OK
        ), HttpStatus.OK);
    }

    @GetMapping("/doctor/today")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<ApiDataResponse<List<AppointmentResponseDTO>>> doctorTodayAppointments() {
        return new ResponseEntity<>(new ApiDataResponse<>(
                true,
                "Lấy danh sách lịch hẹn hôm nay của bác sĩ thành công!",
                appointmentService.doctorTodayAppointments(),
                HttpStatus.OK
        ), HttpStatus.OK);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiDataResponse<List<AppointmentResponseDTO>>> getAllAppointments() {
        return new ResponseEntity<>(new ApiDataResponse<>(
                true,
                "Lấy toàn bộ danh sách lịch hẹn thành công!",
                appointmentService.getAllAppointments(),
                HttpStatus.OK
        ), HttpStatus.OK);
    }
}