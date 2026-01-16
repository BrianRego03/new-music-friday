package com.brian.newfriday.repository;

import com.brian.newfriday.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
    User findByEmail(String email);
    boolean existsByEmail(String email);
}
