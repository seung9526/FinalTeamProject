package com.springboot.Teamproject.service;

import com.springboot.Teamproject.entity.Product;
import com.springboot.Teamproject.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    //findAll메서드에 pageable을 변수로 받아와서 제품의 전체리스트를 받아온다.
    public Page<Product> getList(int pageNumber){

        return productRepository.findAll( PageRequest.of(pageNumber,12));
    }

    //getById메서드에 제품번호를 변수로 받아 상품정보를 받아온다.
    public Product getProduct(int pno){
        return productRepository.getById(pno);
    }

    //검색어와
    public Page<Product> searchProduct(String searchKeyword, Pageable pageable){
        return productRepository.findByNameContaining(searchKeyword,pageable);
    }

    public Page<Product> category(String code,int pageNumber){
        return productRepository.findAllByCode(code, PageRequest.of(pageNumber,12));
    }




}
