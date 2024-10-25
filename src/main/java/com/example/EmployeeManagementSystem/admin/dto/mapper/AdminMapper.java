package com.example.EmployeeManagementSystem.admin.dto.mapper;

import com.example.EmployeeManagementSystem.admin.dto.request.AdminRequest;
import com.example.EmployeeManagementSystem.admin.dto.response.AdminResponse;
import com.example.EmployeeManagementSystem.admin.entity.Admin;


public class AdminMapper {
    public static Admin toEntity(AdminRequest request){
        return Admin.builder()
                .name(request.getName())
                .email(request.getEmail())
                .address(request.getAddress())
                .phoneNumber(request.getPhoneNumber())
                .department(request.getDepartment())
                .build();
    }

    public static AdminResponse toResponse(Admin entity){
        return AdminResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .email(entity.getEmail())
                .address(entity.getAddress())
                .phoneNumber(entity.getPhoneNumber())
                .department(entity.getDepartment())
                .build();
    }
}
