package com.fatouchoco.servicetransport.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "USER_S")
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty(message = "Email is required")
    private String username;

    @NotEmpty(message = "Email is required")
    private String password;
    @NotEmpty(message = "Email is required")
    @Email(message = "Please provide a valid email address")
    private String email;
    private String role;
    private String agency;
}