package com.springboot.Teamproject.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pno;    //상품 번호

    @Column(nullable = false)
    private String name;     //상품 이름

    @Column(nullable = false)
    private int price;    //상품 가격

    @Column(nullable = false)
    private String description;  //상품 정보

    @Column(nullable = false)
    private String imageFileName; //상품 이미지 파일이름

    @Column(nullable = false)
    private String code;    //카테고리 코드

    @OneToMany(mappedBy = "product")
    @ToString.Exclude
    private List<Cart> cartList;      //장바구니 정보

    @OneToMany(mappedBy = "product")
    @ToString.Exclude
    private List<Purchase> purchaseList;    //주문 내역 정보
}
