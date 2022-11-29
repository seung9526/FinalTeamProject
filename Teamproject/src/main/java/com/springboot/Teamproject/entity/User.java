package com.springboot.Teamproject.entity;

import jdk.jshell.Snippet;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @Column(nullable = false,unique = true)
    @NotBlank
    private String id;      //아이디

    @Column(nullable = false)
    @NotBlank
    private String password;    //비밀번호

    @Column(nullable = false,unique = true)
    @NotBlank
    private String nickname;    //닉네임

    @OneToMany(mappedBy = "userprofile")
    private List<Cart> cartList;      //장바구니 정보

    @OneToMany(mappedBy = "userprofile", cascade = CascadeType.REMOVE)
    @ToString.Exclude
    private List<BlogBoard> boardList;  //게시판 일대다 관계

    @OneToMany(mappedBy = "userprofile" ,cascade = CascadeType.REMOVE)
    @ToString.Exclude
    private List<Comment> commentList;  //댓글 일대다 관계

    @OneToMany(mappedBy = "userprofile" ,cascade = CascadeType.REMOVE)
    private List<Purchase> purchaseList;    //주문 내역 정보 일대다 관계

}
