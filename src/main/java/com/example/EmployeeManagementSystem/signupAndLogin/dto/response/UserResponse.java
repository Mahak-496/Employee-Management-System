package com.example.EmployeeManagementSystem.signupAndLogin.dto.response;

import com.example.EmployeeManagementSystem.signupAndLogin.entity.EUserRole;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {
    private int id;
    private String username;
    private EUserRole role;
    private String email;
    private String token;
    private  String refreshToken;
}
