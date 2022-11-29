package com.springboot.Teamproject.DTO;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;

@Setter
@Getter
public class UserCreateForm {

    private String id;      //아이디

    @Size(min = 1, max = 4, message = "비밀번호는 최소 8글자 이상이어야 합니다")
    private String password1;   //비밀번호

    private String password2;   //비밀번호 확인

    private String nickname;    //닉네임
}
