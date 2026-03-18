package com.telusko.service;

import com.telusko.model.UserInfo;
import com.telusko.model.UserPrincipal;
import com.telusko.repo.UserInfoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


// UserInfoService provides user-related services including loading user details
//  and managing user data in the repository.

@Service
public class UserInfoService implements UserDetailsService {

    @Autowired
    private UserInfoRepo userInfoRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;


    // Loads a user's details given their userName.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserInfo> userInfo = userInfoRepo.findByUserName(username);
        return userInfo.map(UserPrincipal::new)
                .orElseThrow(() -> new UsernameNotFoundException("UserName not found: " + username));
    }


    // Adds a new user to the repository and encrypting password before saving it.
    public String addUser(UserInfo userInfo) {
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        userInfoRepo.save(userInfo);
        return "user added successfully";
    }


}
