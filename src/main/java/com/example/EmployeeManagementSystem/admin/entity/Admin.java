package com.example.EmployeeManagementSystem.admin.entity;

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
@Table(name = "admin")
@AllArgsConstructor
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    @NotEmpty(message = "Name is required")
    @Column(name = "admin_name")
    private String name;

    @NotEmpty(message = "email is required")
    @Email(message = "Email should be valid")
    @Column(unique = true, name = "admin_email")
    private String email;

    @Column(name = "admin_address")
    private String address;

    @NotEmpty(message = "Phone Number is required")
    @Column(unique = true, name = "admin_phone_number")
    @Pattern(regexp = "\\d{10}", message = "Phone number must be 10 digits")
    private String phoneNumber;

    @Column(name = "admin_department")
    private String department;

    @CreationTimestamp
    private Timestamp createdOn;

    @UpdateTimestamp
    private Timestamp updateOn;

}
