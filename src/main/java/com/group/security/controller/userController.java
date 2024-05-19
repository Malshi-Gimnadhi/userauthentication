package com.group.security.controller;

import com.group.security.entity.AuthRequest;
import com.group.security.entity.UserInfo;
import com.group.security.service.JwtService;
import com.group.security.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/auth")
public class userController {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/addUser")
    public String addUser(@RequestBody UserInfo userInfo) {
        return userInfoService.addUser(userInfo);
    }

    @PostMapping("/login")
    public String login(@RequestBody AuthRequest authRequest) {
        String token = userInfoService.login(authRequest.getUsername(), authRequest.getPassword());
        if (token != null) {
            return token;
        }
        throw new UsernameNotFoundException("Invalid username or password");
    }

    @GetMapping("/getUsers")
    //@PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<UserInfo> getAllUsers() {
        return userInfoService.getAllUser();
    }

    @GetMapping("/getUsers/{id}")
    //@PreAuthorize("hasAuthority('ROLE_USER')")
    public UserInfo getUserById(@PathVariable Integer id) {
        return userInfoService.getUser(id);
    }

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome to Spring Security !!";
    }
}
