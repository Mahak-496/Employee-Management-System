package com.example.EmployeeManagementSystem.admin.service;

import com.example.EmployeeManagementSystem.admin.dto.request.AdminRequest;
import com.example.EmployeeManagementSystem.admin.dto.response.AdminResponse;

import java.util.List;
import java.util.Optional;

public interface IAdminService {
    List<AdminResponse> getAllAdmins();

    AdminResponse addAdmin(AdminRequest adminRequest);

    Optional<AdminResponse> getAdminByID(int id);

    void deleteAdmin(int id);

    boolean checkIfAdminExists(int id);

    AdminResponse updateAdmin(AdminRequest adminRequest, int id);
}
