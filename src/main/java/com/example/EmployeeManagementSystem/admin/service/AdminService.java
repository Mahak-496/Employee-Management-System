package com.example.EmployeeManagementSystem.admin.service;

import com.example.EmployeeManagementSystem.admin.dto.mapper.AdminMapper;
import com.example.EmployeeManagementSystem.admin.dto.request.AdminRequest;
import com.example.EmployeeManagementSystem.admin.dto.response.AdminResponse;
import com.example.EmployeeManagementSystem.admin.entity.Admin;
import com.example.EmployeeManagementSystem.admin.repository.AdminRepository;
import com.example.EmployeeManagementSystem.exceptions.AdminNotFoundException;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
@Service
public class AdminService implements IAdminService{
    @Autowired
    private AdminRepository adminRepository;

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    private static final Pattern PHONE_PATTERN = Pattern.compile("\\d{10}");

    @Override
    public List<AdminResponse> getAllAdmins(){
        List<Admin> admin=adminRepository.findAll();
        if(admin.isEmpty()){
            throw new AdminNotFoundException("No Admin Found");
        }
        return admin.stream()
                .map(AdminMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public AdminResponse addAdmin(AdminRequest adminRequest) {
        validateAdminRequestDTO(adminRequest);
        Admin admin = AdminMapper.toEntity(adminRequest);
        Admin saveAdmin = adminRepository.save(admin);
        return AdminMapper.toResponse(saveAdmin);
    }


    @Override
    public Optional<AdminResponse> getAdminByID(int id) {
        return Optional.ofNullable(adminRepository.findById(id)
                .map(AdminMapper::toResponse)
                .orElseThrow(() -> new AdminNotFoundException("Admin not found with id: " + id)));
    }


    @Override
    public void deleteAdmin(int id) {
        if (!adminRepository.existsById(id)) {
            throw new AdminNotFoundException("No Admin found with id: " + id);
        }
        adminRepository.deleteById(id);
    }

    @Override
    public boolean checkIfAdminExists(int id) {
        return adminRepository.existsById(id);
    }


    @Override
    public AdminResponse updateAdmin(AdminRequest adminRequest, int id) {
        if (!adminRepository.existsById(id)) {
            throw new AdminNotFoundException("Admin not found with ID: " + id);
        }
        Admin existingAdmin = adminRepository.findById(id)
                .orElseThrow(() -> new AdminNotFoundException("Admin not found with ID: " + id));

        Admin updatedAdmin = AdminMapper.toEntity(adminRequest);
        updatedAdmin.setId(id);
        Admin savedAdmin = adminRepository.save(updatedAdmin);
        return AdminMapper.toResponse(savedAdmin);
    }


    private void validateAdminRequestDTO(AdminRequest dto) {
        if (dto.getEmail() == null || dto.getEmail().isEmpty()) {
            throw new ValidationException("Email cannot be empty");
        }
        if (!EMAIL_PATTERN.matcher(dto.getEmail()).matches()) {
            throw new ValidationException("Email format is incorrect");
        }
        if (dto.getPhoneNumber() == null || dto.getPhoneNumber().length() != 10) {
            throw new ValidationException("Phone number must be 10 digits");
        }
        if (!PHONE_PATTERN.matcher(dto.getPhoneNumber()).matches()) {
            throw new ValidationException("Phone number format is incorrect");
        }
    }


}
