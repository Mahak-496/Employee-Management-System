package com.example.EmployeeManagementSystem.signupAndLogin.dto.mapper;

import com.example.EmployeeManagementSystem.signupAndLogin.dto.request.UserRequest;
import com.example.EmployeeManagementSystem.signupAndLogin.dto.response.UserResponse;
import com.example.EmployeeManagementSystem.signupAndLogin.entity.User;

public class UserMapper {

    public static User toUserEntity(UserRequest userRequest) {
        return User.builder()
                .username(userRequest.getUsername())
                .role(userRequest.getRole())
                .email(userRequest.getEmail())
                .password(userRequest.getPassword())
                .build();

    }

    public static UserResponse toResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .role(user.getRole())
                .email(user.getEmail())
                .token(user.getToken())
                .refreshToken(user.getRefreshToken())
                .build();
    }
}
