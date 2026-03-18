package com.telusko.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The AuthRequest class is used to store and transfer user authentication data.
 * It includes a userName and password, which are typically provided by the user
 * when attempting to log in.
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {

    // Property to store the userName
    private String userName;

    // Property to store the password
    private String password;
}
