package com.springboot.Teamproject;

import lombok.Getter;


//일반 유저와 관리자 유저를 구분하기 위한 값 설정
@Getter
public enum UserRole {

    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");

    UserRole(String value){
        this.value = value;
    }

    private String value;
}
