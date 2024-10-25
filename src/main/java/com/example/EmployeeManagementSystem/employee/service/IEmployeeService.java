package com.example.EmployeeManagementSystem.employee.service;

import com.example.EmployeeManagementSystem.employee.dto.request.EmployeeRequest;
import com.example.EmployeeManagementSystem.employee.dto.response.EmployeeResponse;
import com.example.EmployeeManagementSystem.signupAndLogin.entity.User;

import java.util.List;
import java.util.Optional;

public interface IEmployeeService {
//    List<EmployeeResponse> getAllEmployees();

//
//    List <EmployeeResponse> getAllEmployees(User admin);

    List <EmployeeResponse> getAllEmployees();

    EmployeeResponse addEmployee(EmployeeRequest employeeRequestDTO, User admin);

    Optional<EmployeeResponse> getEmployeesByID(int id);

    void deleteEmployee(int id);

    boolean checkIfEmployeeExists(int id);


    EmployeeResponse updateEmployee(EmployeeRequest employeeRequestDTO, int id, User admin);
}
