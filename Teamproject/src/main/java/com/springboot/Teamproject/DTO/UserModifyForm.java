package com.springboot.Teamproject.DTO;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;

@Getter
@Setter
public class UserModifyForm {

    private String id;      //아이디

    private String nickname;    //닉네임
}
