package com.springboot.Teamproject.service;

import com.springboot.Teamproject.entity.ImageFile;
import com.springboot.Teamproject.repository.ImageFileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageFileService {

    private final ImageFileRepository imageFileRepository;

    //해당 번호의 게시판에 등록된 이미지 파일 정보를 가져옴
    public List<ImageFile> getImageFiles(int bno){

        return this.imageFileRepository.findAllByboardBno(bno);
    }

    public void delete(int fno){

        //해당 이미지 파일 정보의 경로 정보를 가져옴
        File file = new File(this.imageFileRepository.getById(fno).getSavedPath());

        //해당 파일을 삭제
        file.delete();

        //해당 파일 정보를 삭제
        this.imageFileRepository.deleteById(fno);
    }
}
