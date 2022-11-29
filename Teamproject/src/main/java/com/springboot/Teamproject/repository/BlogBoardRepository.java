package com.springboot.Teamproject.repository;

import com.springboot.Teamproject.entity.BlogBoard;
import com.springboot.Teamproject.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BlogBoardRepository extends JpaRepository<BlogBoard, Integer> {

    Page<BlogBoard> findAllByUserprofile(User user, Pageable pageable);

    Page<BlogBoard> findAllByUserprofileAndTitleContaining(User user, String search, Pageable pageable);

    Optional<BlogBoard> findFirstByUserprofileOrderByBnoDesc(User user);
}
