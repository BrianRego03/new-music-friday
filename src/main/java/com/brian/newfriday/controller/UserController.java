package com.brian.newfriday.controller;

import com.brian.newfriday.dtos.*;
import com.brian.newfriday.entity.Role;
import com.brian.newfriday.mappers.UserMapper;
import com.brian.newfriday.repository.UserRepository;
import com.brian.newfriday.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
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
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        Integer identity = (Integer) authentication.getPrincipal();
        if(!identity.equals(id)){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
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
        if(!(passwordEncoder.matches(passwordRequest.getOldPassword(),user.getPassword()))){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/users/favourites/{artistSpotifyId}")
    public ResponseEntity<?> addArtistToUser(@PathVariable String artistSpotifyId){
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        Integer identity = (Integer) authentication.getPrincipal();
        userService.addArtistToUser(identity,artistSpotifyId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/users/favourites")
    public ResponseEntity<List<SpotifyArtistDto>> getUserArtists(){
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        Integer identity = (Integer) authentication.getPrincipal();
        var artistDtos = userService.getUserArtistsDto(identity);
        return ResponseEntity.ok(artistDtos);
    }

    @DeleteMapping("/users/favourites/{artistSpotifyId}")
    public ResponseEntity<?> removeArtistFromUser(@PathVariable String artistSpotifyId){
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        Integer identity = (Integer) authentication.getPrincipal();
        userService.removeArtistFromUser(identity,artistSpotifyId);
        return ResponseEntity.noContent().build();
    }



}
