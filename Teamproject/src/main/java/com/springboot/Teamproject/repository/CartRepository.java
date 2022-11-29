package com.springboot.Teamproject.repository;

import com.springboot.Teamproject.entity.Cart;
import com.springboot.Teamproject.entity.Product;
import com.springboot.Teamproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Integer> {

    boolean existsByProductAndUserprofile(Product product,User user);
    List<Cart> findAllByUserprofile(User user); //유저정보에 대한 Cart 의 List 를 가져오는 Repository
}
