package com.telusko.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The UserInfo class represents a user entity in the application.
 * It is used to store user-related data such as name, email, roles, and password.
 * This class is annotated as an Entity, which means it is a JPA entity
 * and is mapped to a database table.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String userName;
    private String email;
    private String phoneNumber;
    private String address;
    private String password;

}
