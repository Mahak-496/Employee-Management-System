package com.example.EmployeeManagementSystem.employee.repository;

import com.example.EmployeeManagementSystem.employee.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Integer> {

}
