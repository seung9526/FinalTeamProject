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
public class ImageFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int fno;    //파일 번호

    private String originName;      //업로드할 때 파일명

    private String savedName;       //서버에 저장될 때 파일명

    private String savedPath;       //파일 경로

    @ManyToOne
    @JoinColumn(name = "board_id")
    @ToString.Exclude
    private BlogBoard board;        //게시판 정보 다대일 관계

}
