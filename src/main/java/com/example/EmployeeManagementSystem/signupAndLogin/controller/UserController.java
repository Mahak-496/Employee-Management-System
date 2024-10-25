package com.example.EmployeeManagementSystem.signupAndLogin.controller;

import com.example.EmployeeManagementSystem.signupAndLogin.dto.response.TokenResponse;
import com.example.EmployeeManagementSystem.signupAndLogin.dto.request.TokenRequest;
import com.example.EmployeeManagementSystem.signupAndLogin.dto.request.UserRequest;
import com.example.EmployeeManagementSystem.signupAndLogin.dto.response.UserResponse;
import com.example.EmployeeManagementSystem.signupAndLogin.service.UserService;
import com.example.EmployeeManagementSystem.utils.ApiResponse;
import com.example.EmployeeManagementSystem.utils.ResponseSender;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private UserService userService;


    @PostMapping("/api/auth/register")
    public ResponseEntity<Object> registerUser(@RequestBody @Validated UserRequest userRequest) {
        try {
            UserResponse response = userService.registerUser(userRequest);
            ApiResponse apiResponse = ApiResponse.builder()
                    .message("User registered successfully")
                    .data(response)
                    .statusCode(HttpStatus.CREATED.value())
                    .build();
            return ResponseSender.send(apiResponse);
        } catch (ValidationException e) {
            ApiResponse apiResponse = ApiResponse.builder()
                    .message(e.getMessage())
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .build();
            return ResponseSender.send(apiResponse);
        }catch (Exception e) {
            ApiResponse apiResponse = ApiResponse.builder()
                    .message(e.getMessage())
                    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .build();
            return ResponseSender.send(apiResponse);
        }
    }

    @PostMapping("/api/auth/login")
    public ResponseEntity<Object> loginUser(@RequestBody UserRequest userRequest) {
        try {
            UserResponse response = userService.loginUser(userRequest);
            ApiResponse apiResponse = ApiResponse.builder()
                    .message("Login successful")
                    .data(response)
                    .statusCode(HttpStatus.OK.value())
                    .build();
            return ResponseSender.send(apiResponse);
        }
        catch (ValidationException e) {
            ApiResponse apiResponse = ApiResponse.builder()
                    .message(e.getMessage())
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .build();
            return ResponseSender.send(apiResponse);
        }catch (Exception e) {
            ApiResponse apiResponse = ApiResponse.builder()
                    .message("An error occurred")
                    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .build();
            return ResponseSender.send(apiResponse);
        }
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception e) {
        ApiResponse apiResponse = ApiResponse.builder()
                .message("An unexpected error occurred: " + e.getMessage())
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .build();
        return ResponseSender.send(apiResponse);
    }

    @PostMapping("/api/auth/refresh")
    public ResponseEntity<Object> refresh(@RequestBody TokenRequest refreshTokenRequest) {
        String refreshToken = refreshTokenRequest.getRefreshToken();

        try {
            TokenResponse tokenResponse = userService.refreshToken(refreshToken);

            ApiResponse apiResponse = ApiResponse.builder()
                    .message("Tokens refreshed successfully")
                    .data(tokenResponse)
                    .statusCode(HttpStatus.OK.value())
                    .build();
            return ResponseSender.send(apiResponse);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.builder()
                            .message("Refresh token is invalid" )
                            .statusCode(HttpStatus.UNAUTHORIZED.value())
                            .build());
        }
    }
}

