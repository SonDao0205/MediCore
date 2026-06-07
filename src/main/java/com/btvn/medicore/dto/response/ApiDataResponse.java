package com.btvn.medicore.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class ApiDataResponse<T> {
    private Boolean success;
    private String message;
    private T data;
    private HttpStatus status;
}
