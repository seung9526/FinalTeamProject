package com.springboot.Teamproject;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//DB에서 정보를 가져오지 못했을 때 보여줄 예외 처리
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "entity not found")
public class DataNotFoundException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public DataNotFoundException(String message){
        super(message);
    }
}
