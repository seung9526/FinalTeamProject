package com.springboot.Teamproject.repository;

import com.springboot.Teamproject.entity.Purchase;
import com.springboot.Teamproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

    // 리스트로 유저의 유저정보를 출력하기 위해 findAllByUserprofile에 유저 를 담음
    List<Purchase> findAllByUserprofileOrderByPurchaseNumberDesc(User user);

}
