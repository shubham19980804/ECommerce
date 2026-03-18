
package com.telusko.controller;

import com.telusko.model.AuthRequest;
import com.telusko.model.UserInfo;
import com.telusko.service.JwtService;
import com.telusko.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class UserController {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;


    // Endpoint to add a new user
    @PostMapping("/addUser")
    public String addUser(@RequestBody UserInfo userInfo) {
        return userInfoService.addUser(userInfo);
    }

    // Endpoint for user login and token generation
    @PostMapping("/login")
    public String loginUser(@RequestBody AuthRequest authRequest) {
        // Authenticate the user
        Authentication authenticate = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword()));

        // If authentication is successful, generate and return a JWT token
        if (authenticate.isAuthenticated()) {
            return jwtService.generateToken(authRequest.getUserName());
        } else {
            throw new UsernameNotFoundException("Invalid User Request");
        }
    }


}

