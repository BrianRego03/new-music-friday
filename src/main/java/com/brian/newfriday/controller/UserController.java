package com.brian.newfriday.controller;

import com.brian.newfriday.dtos.ChangePasswordRequest;
import com.brian.newfriday.dtos.RegisterUserRequest;
import com.brian.newfriday.dtos.UpdateUserRequest;
import com.brian.newfriday.dtos.UserDto;
import com.brian.newfriday.entity.Role;
import com.brian.newfriday.mappers.UserMapper;
import com.brian.newfriday.repository.UserRepository;
import com.brian.newfriday.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;

@RestController
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserController (UserService userService, UserMapper userMapper, UserRepository userRepository,
                            PasswordEncoder passwordEncoder){
        this.userService=userService;
        this.userMapper=userMapper;
        this.userRepository=userRepository;
        this.passwordEncoder=passwordEncoder;

    }

    @GetMapping("/users")
    public List<UserDto> getAllUsers(@RequestParam(required = false,defaultValue = "",name = "sort") String sort){
        var users = userService.getAllUsers(sort);

        return users.stream()
                .map(userMapper::toDto)
                .toList();
    }

    @GetMapping("/users/{id}")
    public UserDto getUserById(@PathVariable int id){
        var user = userService.getById(id);
        return userMapper.toDto(user);
    }

    @PostMapping("/users")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterUserRequest userRequest,
           UriComponentsBuilder uriBuilder){
        if(userRepository.existsByEmail(userRequest.getEmail())){
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("email","Email is already in use"));
        }
        var user = userMapper.toEntity(userRequest);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);
        userRepository.save(user);
        var userDto = userMapper.toDto(user);
        var uri = uriBuilder.path("/users/{id}")
                .buildAndExpand(userDto.getId())
                .toUri();
        return ResponseEntity.created(uri).body(userDto);

    }

    @PutMapping("/users/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable int id,
                                              @Valid @RequestBody UpdateUserRequest userRequest){
        var user = userRepository.findById(id).orElse(null);
        if(user==null){
            return ResponseEntity.notFound().build();
        }
        userMapper.update(userRequest,user);
        userRepository.save(user);
        var userDto = userMapper.toDto(user);
        return ResponseEntity.ok(userDto);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable int id){
        if(!userRepository.existsById(id)){
            return ResponseEntity.notFound().build();
        }
        userRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/users/{id}/change-password")
    public ResponseEntity<?> changePassword(@PathVariable int id,
                                            @RequestBody ChangePasswordRequest passwordRequest){
        var user = userRepository.findById(id).orElse(null);
        if(user==null){
            return ResponseEntity.notFound().build();
        }
        String newPassword = passwordRequest.getNewPassword();
        if(newPassword==null || newPassword.isBlank()){
            return ResponseEntity.badRequest()
                    .body(Map.of("newPassword","New password must not be blank"));
        }
        if(!(user.getPassword().equals(passwordRequest.getOldPassword()))){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        user.setPassword(newPassword);
        userRepository.save(user);

        return ResponseEntity.noContent().build();
    }



}
