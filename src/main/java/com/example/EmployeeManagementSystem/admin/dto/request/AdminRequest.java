package com.example.EmployeeManagementSystem.admin.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminRequest {
    private String name;
    private String email;
    private String address;
    private String phoneNumber;
    private String department;
}
