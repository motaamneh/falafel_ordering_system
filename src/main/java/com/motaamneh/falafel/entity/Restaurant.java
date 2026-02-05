package com.motaamneh.falafel.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "restaurants")
@Entity
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    @NotNull(message = "Restaurant name cannot be null")
    @Size(min = 2,max = 100, message = "Restaurant name should be between 2 and 100 characters")
    private String name;

    @Column(nullable = false, length = 100, unique = true)
    @Email(message = "Email should be Valid")
    @NotNull(message = "Email cannot be null")
    private String email;

    @Column(nullable = false)
    @NotNull(message = "Password cannot be null")
    @Size(min = 8,max = 100,message = "Password has to be between 8 and 100 characters")
    private String password;

    @Column(nullable = false)
    @NotNull(message = "Address cannot be null")
    @Size(min = 2,max = 100, message = "Address has to be between 2 and 100 characters")
    private String address;

    @Column(nullable = false)
    @NotNull(message = "phone number cannot be null")
    @Size(min = 8,max = 20,message = "Phone number has to be between 8 and 20 characters")
    private String phone;


    @Column(name = "is_enabled", nullable = false)
    private Boolean isEnabled = true;

    @Column(name = "created_at", updatable = false, nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;




}
