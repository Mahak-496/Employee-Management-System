package com.example.EmployeeManagementSystem.employee.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeResponse {
    private int id;
    private String name;
    private String email;
    private String address;
    private String phoneNumber;
}
