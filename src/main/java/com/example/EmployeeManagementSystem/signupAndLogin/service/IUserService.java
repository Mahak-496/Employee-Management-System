package com.example.EmployeeManagementSystem.signupAndLogin.service;

import com.example.EmployeeManagementSystem.signupAndLogin.dto.request.UserRequest;
import com.example.EmployeeManagementSystem.signupAndLogin.dto.response.UserResponse;

import javax.naming.AuthenticationException;

public interface IUserService {
    UserResponse registerUser(UserRequest userRequest);

    String generateToken(String email);

    UserResponse loginUser(UserRequest userRequest) throws AuthenticationException;
}
