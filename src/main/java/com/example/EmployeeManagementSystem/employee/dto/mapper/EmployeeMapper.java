package com.example.EmployeeManagementSystem.employee.dto.mapper;

import com.example.EmployeeManagementSystem.employee.dto.request.EmployeeRequest;
import com.example.EmployeeManagementSystem.employee.dto.response.EmployeeResponse;
import com.example.EmployeeManagementSystem.employee.entity.Employee;

public class EmployeeMapper {

    public static Employee toEntity(EmployeeRequest request){
        return Employee.builder()
                .name(request.getName())
                .email(request.getEmail())
                .address(request.getAddress())
                .phoneNumber(request.getPhoneNumber())
                .build();
    }

    public static EmployeeResponse toResponse(Employee entity){
        return EmployeeResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .email(entity.getEmail())
                .address(entity.getAddress())
                .phoneNumber(entity.getPhoneNumber())
                .build();
    }
}
