package com.example.EmployeeManagementSystem.utils;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiResponse {
    private String message;
    private int statusCode;
    private Object data;
    private String devMessage;

}
