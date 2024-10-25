package com.example.EmployeeManagementSystem.admin.repository;

import com.example.EmployeeManagementSystem.admin.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin,Integer> {
}
