package com.springboot.Teamproject.repository;

import com.springboot.Teamproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,String> {
}
