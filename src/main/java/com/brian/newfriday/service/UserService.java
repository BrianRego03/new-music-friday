package com.brian.newfriday.service;

import com.brian.newfriday.entity.User;
import com.brian.newfriday.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    @Transactional
    public User registerUser(String name, String email, String password){

        User usernew = new User(name,email,password);
        return userRepository.save(usernew);
    }

    public User getByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public List<User> getAllUsers(String sort){
        return userRepository.findAll(Sort.by(sort));
    }

    @Transactional
    public String DeleteUser(int id){
        userRepository.deleteById(id);
        return "Success";
    }

}
