package com.brian.newfriday.controller;

import com.brian.newfriday.dtos.UserDto;
import com.brian.newfriday.mappers.UserMapper;
import com.brian.newfriday.repository.UserRepository;
import com.brian.newfriday.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    public UserController (UserService userService, UserMapper userMapper, UserRepository userRepository
        ){
        this.userService=userService;
        this.userMapper=userMapper;
        this.userRepository=userRepository;

    }

    @GetMapping("/users")
    public List<UserDto> getAllUsers(@RequestParam(required = false,defaultValue = "",name = "sort") String sort){
        var users = userService.getAllUsers(sort);

        return users.stream()
                .map(userMapper::toDto)
                .toList();
    }
}
