package com.example.EmployeeManagementSystem.admin.controller;

import com.example.EmployeeManagementSystem.admin.dto.request.AdminRequest;
import com.example.EmployeeManagementSystem.admin.dto.response.AdminResponse;
import com.example.EmployeeManagementSystem.admin.service.AdminService;
import com.example.EmployeeManagementSystem.exceptions.EmployeeNotFoundException;
import com.example.EmployeeManagementSystem.utils.ApiResponse;
import com.example.EmployeeManagementSystem.utils.ResponseSender;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@RestController
public class AdminController {
    @Autowired
    private AdminService service;

    @GetMapping("/allAdmin")
    public ResponseEntity<Object> getAllAdmins() {
        try {
            List<AdminResponse> list = service.getAllAdmins();
            ApiResponse apiResponse = ApiResponse.builder()
                    .message("Admins retrieved successfully")
                    .data(list)
                    .statusCode(HttpStatus.OK.value())
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


    @PostMapping("/addAdmin")
    public ResponseEntity<Object> addAdmin(@RequestBody AdminRequest adminRequest) {
        try {
            AdminResponse addAdmin = service.addAdmin(adminRequest);

            ApiResponse apiResponse = ApiResponse.builder()
                    .message("Admin added successfully")
                    .data(addAdmin)
                    .statusCode(HttpStatus.CREATED.value())
                    .build();
            return ResponseSender.send(apiResponse);

        }  catch (ValidationException e) {
            ApiResponse apiResponse = ApiResponse.builder()
                    .message(e.getMessage())
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .build();
            return ResponseSender.send(apiResponse);
        }  catch (Exception e) {
            ApiResponse apiResponse = ApiResponse.builder()
                    .message("Something went wrong")
                    .devMessage(e.getMessage())
                    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .build();
            return ResponseSender.send(apiResponse);
        }
    }


    @GetMapping("/getAdmin/id/{id}")
    public ResponseEntity<Object> getAdminById(@PathVariable int id) {
        ApiResponse apiResponse;
        try {
            Optional<AdminResponse> admin = service.getAdminByID(id);
            apiResponse = ApiResponse.builder()
                    .message("Admin retrieved successfully using ID")
                    .data(admin.get())
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


    @DeleteMapping("/deleteAdmin/{id}")
    public ResponseEntity<Object> deleteAdmin(@PathVariable("id") int id) {
        ApiResponse apiResponse;

        try {

            boolean adminExists = service.checkIfAdminExists(id);
            if (adminExists) {
                service.deleteAdmin(id);
                apiResponse = ApiResponse.builder()
                        .message("Admin deleted successfully")
                        .statusCode(HttpStatus.OK.value())
                        .build();
                return ResponseSender.send(apiResponse);
            } else {
                apiResponse = ApiResponse.builder()
                        .message("Admin with ID " + id + " does not exist")
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

    @PutMapping("/UpdateAdmin/{id}")
    public ResponseEntity<Object> updateEmployee(@PathVariable("id") int id, @RequestBody AdminRequest adminRequest) {
        ApiResponse apiResponse;

        try {
            AdminResponse updatedEmployee = service.updateAdmin(adminRequest, id);
            apiResponse = ApiResponse.builder()
                    .message("Admin updated successfully using ID")
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
}
