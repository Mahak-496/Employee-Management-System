package com.example.EmployeeManagementSystem.employee.service;

import com.example.EmployeeManagementSystem.employee.dto.mapper.EmployeeMapper;
import com.example.EmployeeManagementSystem.employee.dto.request.EmployeeRequest;
import com.example.EmployeeManagementSystem.employee.dto.response.EmployeeResponse;
import com.example.EmployeeManagementSystem.employee.entity.Employee;
import com.example.EmployeeManagementSystem.employee.repository.EmployeeRepository;
import com.example.EmployeeManagementSystem.exceptions.EmployeeNotFoundException;
import com.example.EmployeeManagementSystem.signupAndLogin.entity.User;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class EmployeeService implements IEmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    private static final Pattern PHONE_PATTERN = Pattern.compile("\\d{10}");

    @Override
  public List <EmployeeResponse> getAllEmployees(){
      List<Employee> employees=employeeRepository.findAll();
      if(employees.isEmpty()){
          throw new EmployeeNotFoundException("No Employees Found");
      }
      return employees.stream()
              .map(EmployeeMapper::toResponse)
              .collect(Collectors.toList());
  }

    @Override
    public EmployeeResponse addEmployee(EmployeeRequest employeeRequestDTO, User admin) {
        validateEmployeeRequestDTO(employeeRequestDTO);
        Employee employee = EmployeeMapper.toEntity(employeeRequestDTO);
        Employee saveEmployee = employeeRepository.save(employee);
        return EmployeeMapper.toResponse(saveEmployee);
    }


    @Override
    public Optional<EmployeeResponse> getEmployeesByID(int id) {
        return Optional.ofNullable(employeeRepository.findById(id)
                .map(EmployeeMapper::toResponse)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + id)));
    }


    @Override
    public void deleteEmployee(int id) {
        if (!employeeRepository.existsById(id)) {
            throw new EmployeeNotFoundException("No Employee found with id: " + id);
        }
        employeeRepository.deleteById(id);
    }

    @Override
    public boolean checkIfEmployeeExists(int id) {
        return employeeRepository.existsById(id);
    }


    @Override
    public EmployeeResponse updateEmployee(EmployeeRequest employeeRequestDTO, int id, User admin) {
        if (!employeeRepository.existsById(id)) {
            throw new EmployeeNotFoundException("Employee not found with ID: " + id);
        }
        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with ID: " + id));

        Employee updatedEmployee = EmployeeMapper.toEntity(employeeRequestDTO);
        updatedEmployee.setId(id);
        Employee savedEmployee = employeeRepository.save(updatedEmployee);
        return EmployeeMapper.toResponse(savedEmployee);
    }


    private void validateEmployeeRequestDTO(EmployeeRequest dto) {
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
