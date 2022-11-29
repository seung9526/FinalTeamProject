package com.springboot.Teamproject.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentCreateForm {

    private String comment; //댓글 내용

    private int bno;        //게시글 번호
}
