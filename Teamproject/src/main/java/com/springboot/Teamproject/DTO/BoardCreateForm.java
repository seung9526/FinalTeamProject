package com.springboot.Teamproject.DTO;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
public class BoardCreateForm {

    @NotBlank(message = "제목을 적어주세요")
    private String title;       //제목

    @NotBlank(message = "내용을 적어주세요")
    private String content;     //내용

    private MultipartFile file;    //이미지 파일
}
