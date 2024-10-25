package com.example.EmployeeManagementSystem.signupAndLogin.dto.request;

import com.example.EmployeeManagementSystem.signupAndLogin.entity.EUserRole;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    private String username;
    private EUserRole role;

    private String email;
    private String password;
}
