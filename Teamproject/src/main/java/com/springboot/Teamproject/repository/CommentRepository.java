package com.springboot.Teamproject.repository;

import com.springboot.Teamproject.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Integer> {

    List<Comment> findAllByboardBno(int bno);
}
