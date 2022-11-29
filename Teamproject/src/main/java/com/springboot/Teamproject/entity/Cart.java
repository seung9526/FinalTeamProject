package com.springboot.Teamproject.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int number;

    @ManyToOne
    @JoinColumn(name = "User_id")
    private User userprofile;   //유저 정보

    @ManyToOne
    @JoinColumn(name="product_id")
    private Product product;    //상품 정보

    private int productCount;   //상품 갯수
}
