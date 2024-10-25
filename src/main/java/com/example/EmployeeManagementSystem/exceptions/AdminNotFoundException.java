package com.example.EmployeeManagementSystem.exceptions;

public class AdminNotFoundException extends RuntimeException{
    public AdminNotFoundException(String message) {
        super(message);
    }
}
