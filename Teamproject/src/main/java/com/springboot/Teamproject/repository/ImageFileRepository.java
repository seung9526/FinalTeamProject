package com.springboot.Teamproject.repository;

import com.springboot.Teamproject.entity.ImageFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageFileRepository extends JpaRepository<ImageFile,Integer> {

    List<ImageFile> findAllByboardBno(int bno);
}
