package com.example.EmployeeManagementSystem.signupAndLogin.dto.request;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TokenRequest {
    private String refreshToken;
}
