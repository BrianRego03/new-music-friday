package com.brian.newfriday.controller;

import com.brian.newfriday.config.JwtConfig;
import com.brian.newfriday.mappers.UserMapper;
import com.brian.newfriday.repository.UserRepository;
import com.brian.newfriday.service.JwtService;
import com.brian.newfriday.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserMapper userMapper;
    private final JwtConfig jwtConfig;

    public AuthController(PasswordEncoder passwordEncoder, UserService userService, UserRepository userRepository, AuthenticationManager authenticationManager, JwtService jwtService, UserMapper userMapper, JwtConfig jwtConfig) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userMapper = userMapper;
        this.jwtConfig = jwtConfig;
    }
}
