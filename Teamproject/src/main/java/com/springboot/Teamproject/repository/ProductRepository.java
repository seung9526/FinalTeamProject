package com.springboot.Teamproject.repository;

import com.springboot.Teamproject.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    Page<Product> findByNameContaining(String searchKeyword,Pageable pageable);

    Page<Product> findByPnoOrderByPnoDesc(Product product, Pageable pageable);


    Page<Product> findAll(Pageable pageable);

    Page<Product> findAllByCode(String code,Pageable pageable);

    Optional<Product> findByPno(int pno);

}
