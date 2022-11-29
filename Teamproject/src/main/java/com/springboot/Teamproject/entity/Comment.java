package com.springboot.Teamproject.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cno;        //댓글 번호

    private String comment; //댓글 내용
    
    private String writer;  //댓글 작성자
    
    private String createDate;  //댓글 작성 날짜

    @ManyToOne
    @JoinColumn(name = "board_id")
    @ToString.Exclude
    private BlogBoard board;    //게시글 다대일 관계
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private User userprofile;   //유저정보 다대일 관계
}
