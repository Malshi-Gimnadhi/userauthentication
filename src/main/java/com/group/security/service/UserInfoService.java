package com.group.security.service;

import com.group.security.entity.UserInfo;
import com.group.security.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserInfoService implements UserDetailsService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService; // Inject JwtService for token generation

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserInfo> userInfo = userInfoRepository.findByName(username);
        return userInfo.map(UserInfoDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found" + username));
    }

    public String addUser(UserInfo userInfo) {
        // Check if the user already exists
        UserInfo existingUser = userInfoRepository.findByName(userInfo.getName()).orElse(null);
        if (existingUser != null) {
            return "The user is already registered";
        }

        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        userInfoRepository.save(userInfo);
        return "User added successfully";
    }

    public List<UserInfo> getAllUser() {
        return userInfoRepository.findAll();
    }

    public UserInfo getUser(Integer id) {
        return userInfoRepository.findById(id).orElse(null);
    }

    public UserInfo getUserByUsername(String username) {
        return userInfoRepository.findByName(username).orElse(null);
    }

    public String login(String username, String password) {
        UserDetails userDetails;
        try {
            userDetails = loadUserByUsername(username);
            if (!passwordEncoder.matches(password, userDetails.getPassword())) {
                return "Invalid username or password";
            }
            String token = jwtService.generateToken(username);
            return "Login successful.\nToken: " + token;
        } catch (UsernameNotFoundException e) {
            return "Invalid username or password";
        }
    }

}
