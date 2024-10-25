package com.example.EmployeeManagementSystem.employee.controller;

import com.example.EmployeeManagementSystem.employee.dto.request.EmployeeRequest;
import com.example.EmployeeManagementSystem.employee.dto.response.EmployeeResponse;
import com.example.EmployeeManagementSystem.employee.service.EmployeeService;
import com.example.EmployeeManagementSystem.exceptions.EmployeeNotFoundException;
import com.example.EmployeeManagementSystem.signupAndLogin.entity.User;
import com.example.EmployeeManagementSystem.signupAndLogin.repository.UserRepository;
import com.example.EmployeeManagementSystem.signupAndLogin.service.UserService;
import com.example.EmployeeManagementSystem.utils.ApiResponse;
import com.example.EmployeeManagementSystem.utils.ResponseSender;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService service;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;


    @RolesAllowed("ROLE_ADMIN")
    @GetMapping("/allEmployees")
    public ResponseEntity<Object> getAllEmployees() {
        try {
            List<EmployeeResponse> list = service.getAllEmployees();
            ApiResponse apiResponse = ApiResponse.builder()
                    .message("Employees retrieved successfully")
                    .data(list)
                    .statusCode(HttpStatus.OK.value())
                    .build();
            return ResponseSender.send(apiResponse);
        }
        catch (UsernameNotFoundException e) {
            ApiResponse apiResponse = ApiResponse.builder()
                    .message(e.getMessage())
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .build();
            return ResponseSender.send(apiResponse);
        } catch (RuntimeException e) {
            ApiResponse apiResponse = ApiResponse.builder()
                    .message(e.getMessage())
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .build();
            return ResponseSender.send(apiResponse);
        }
    }




    @RolesAllowed("ROLE_ADMIN")
    @PostMapping("/add")
    public ResponseEntity<Object> addEmployees(@RequestBody EmployeeRequest employeeRequest) {
        try {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User admin = userRepository.findByEmail(userDetails.getUsername())
                    .orElseThrow(() -> new UsernameNotFoundException("User with email " + userDetails.getUsername() + " not found."));
           EmployeeResponse addedAdmin = service.addEmployee(employeeRequest, admin);

            ApiResponse apiResponse = ApiResponse.builder()
                    .message("Employee added successfully")
                    .data(addedAdmin)
                    .statusCode(HttpStatus.CREATED.value())
                    .build();
            return ResponseSender.send(apiResponse);

        }  catch (ValidationException e) {
            ApiResponse apiResponse = ApiResponse.builder()
                    .message(e.getMessage())
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .build();
            return ResponseSender.send(apiResponse);
        }
    }

    @RolesAllowed("ROLE_ADMIN")
    @GetMapping("/getEmployees/id/{id}")
    public ResponseEntity<Object> getEmployeeById(@PathVariable int id) {
        ApiResponse apiResponse;
        try {
            Optional<EmployeeResponse> employee = service.getEmployeesByID(id);
            apiResponse = ApiResponse.builder()
                    .message("Employee retrieved successfully using ID")
                    .data(employee.get())
                    .statusCode(200)
                    .build();
            return ResponseSender.send(apiResponse);

        } catch (EmployeeNotFoundException e) {
            apiResponse = ApiResponse.builder()
                    .message(e.getMessage())
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .build();
            return ResponseSender.send(apiResponse);
        }
    }

    @RolesAllowed("ROLE_ADMIN")
    @DeleteMapping("/deleteEmployee/{id}")
    public ResponseEntity<Object> deleteEmployee(@PathVariable("id") int id) {
        ApiResponse apiResponse;

        try {

            boolean employeeExists = service.checkIfEmployeeExists(id);
            if (employeeExists) {
                service.deleteEmployee(id);
                apiResponse = ApiResponse.builder()
                        .message("Employee deleted successfully")
                        .statusCode(HttpStatus.OK.value())
                        .build();
                return ResponseSender.send(apiResponse);
            } else {
                apiResponse = ApiResponse.builder()
                        .message("Employee with ID " + id + " does not exist")
                        .statusCode(HttpStatus.NOT_FOUND.value())
                        .build();
                return ResponseSender.send(apiResponse);
            }
        } catch (Exception e) {
            apiResponse = ApiResponse.builder()
                    .message("Something went wrong")
                    .devMessage(e.getMessage())
                    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .build();
            return ResponseSender.send(apiResponse);
        }
    }

    @RolesAllowed("ROLE_ADMIN")
    @PutMapping("/updateEmployee/{id}")
    public ResponseEntity<Object> updateEmployee(@PathVariable("id") int id, @RequestBody EmployeeRequest employeeRequest) {
        ApiResponse apiResponse;

        try {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User admin = userRepository.findByEmail(userDetails.getUsername())
                    .orElseThrow(() -> new UsernameNotFoundException("User with email " + userDetails.getUsername() + " not found."));
            EmployeeResponse updatedEmployee = service.updateEmployee(employeeRequest, id,admin);
            apiResponse = ApiResponse.builder()
                    .message("Employee updated successfully using ID")
                    .statusCode(200)
                    .build();
            return ResponseSender.send(apiResponse);
        }
        catch (EmployeeNotFoundException e) {
            apiResponse = ApiResponse.builder()
                    .message(e.getMessage())
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .build();
            return ResponseSender.send(apiResponse);
        }
    }



//    @RolesAllowed("ROLE_ADMIN")
//    @PutMapping("/UpdateEmployee/{id}")
//    public ResponseEntity<Object> updateEmployee(@PathVariable("id") int id, @RequestBody EmployeeRequest employeeRequest) {
//        try {
//            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//            User admin = userRepository.findByEmail(userDetails.getUsername())
//                    .orElseThrow(() -> new UsernameNotFoundException("User with email " + userDetails.getUsername() + " not found."));
//            EmployeeResponse updatedEmployee = service.updateEmployee(employeeRequest, id,admin);
//
//            ApiResponse apiResponse = ApiResponse.builder()
//                    .message("Employee Updated successfully")
//                    .data(updatedEmployee)
//                    .statusCode(HttpStatus.CREATED.value())
//                    .build();
//            return ResponseSender.send(apiResponse);
//
//        }  catch (ValidationException e) {
//            ApiResponse apiResponse = ApiResponse.builder()
//                    .message(e.getMessage())
//                    .statusCode(HttpStatus.BAD_REQUEST.value())
//                    .build();
//            return ResponseSender.send(apiResponse);
//        }
//    }
//







}
