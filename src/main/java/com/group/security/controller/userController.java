package com.group.security.controller;

import com.group.security.Exception.EventNotFoundException;
import com.group.security.entity.AuthRequest;
import com.group.security.entity.UserInfo;
import com.group.security.service.JwtService;
import com.group.security.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
// userController.java

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/auth")
@Slf4j
public class userController {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/addUser")
    public ResponseEntity<String> addUser(@RequestBody UserInfo userInfo) {
        if (userInfoService.isEmailRegistered(userInfo.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("This email is already registered");
        }

        userInfoService.addUser(userInfo);
        return ResponseEntity.status(HttpStatus.CREATED).body("User added successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AuthRequest authRequest) {
        String token = userInfoService.login(authRequest.getUsername(), authRequest.getPassword());
        if (token != null) {
            return ResponseEntity.ok(token);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }

    @GetMapping("/getUsers")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<UserInfo> getAllUsers() {
        return userInfoService.getAllUser();
    }

    @GetMapping("/getUser/{id}")
    public ResponseEntity<UserInfo> getUser(@PathVariable int id) {
        UserInfo userInfo = userInfoService.getUser(id);
        return ResponseEntity.ok(userInfo);
    }

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome to Security !!";
    }

    @GetMapping("/getUsername/{id}")
    public ResponseEntity<String> getUsernameById(@PathVariable Integer id) {
        String username = userInfoService.getUsernameById(id);
        if (username != null) {
            return ResponseEntity.ok(username);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }
}