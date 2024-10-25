package com.example.EmployeeManagementSystem.employee.entity;

//import com.example.EmployeeManagementSystem.signupAndLogin.entity.User;
import com.example.EmployeeManagementSystem.signupAndLogin.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;


@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "Employees")
@AllArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @NotEmpty(message = "name is required")
    @Column(name = "employee_name")
    private String name;

    @NotEmpty(message = "email is required")
    @Email(message = "Email should be valid")
    @Column(unique = true, name = "employee_email")
    private String email;

    @Column(name = "employee_address")
    private String address;
    @NotEmpty(message = "Phone Number is required")
    @Column(unique = true, name = "employee_phone_number")
    @Pattern(regexp = "\\d{10}", message = "Phone number must be 10 digits")
    private String phoneNumber;


    @ManyToOne
    @JoinColumn(name = "admin_id")
    private User admin;

    @CreationTimestamp
    private Timestamp createdOn;

    @UpdateTimestamp
    private Timestamp updateOn;

}
