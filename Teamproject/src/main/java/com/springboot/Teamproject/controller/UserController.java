package com.springboot.Teamproject.controller;

import com.springboot.Teamproject.DTO.UserCreateForm;
import com.springboot.Teamproject.DTO.UserModifyForm;
import com.springboot.Teamproject.entity.User;
import com.springboot.Teamproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    //회원가입 시 정보를 등록
    @PostMapping("/signup")
    public String signup(@Valid UserCreateForm userCreateForm, BindingResult bindingResult, Model model){

        if(bindingResult.hasErrors())
            return "user/user_form";

        if(!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())){       //패스워드와 패스워드 확인의 정보를 비교

            bindingResult.rejectValue("password2","passwordInCorrect","2개의 패스워드가 일치하지 않습니다");
            System.out.println("2개의 패스워드가 일치하지 않습니다.");

            model.addAttribute("check", false);

            return "user/user_form";
        }

        if(this.userService.isUser(userCreateForm.getId())){                //회원가입 시 데이터베이스에 있는 ID인지 비교
            bindingResult.rejectValue("id","","이미 등록된 사용자입니다.");
            System.out.println("이미 등록된 사용자입니다");

            model.addAttribute("check", false);

            return "user/user_form";
        }

        this.userService.create(userCreateForm.getId(),userCreateForm.getPassword1(),userCreateForm.getNickname());  //등록

        model.addAttribute("check", true);

        return "redirect:/user/login";
    }

    //로그인 홈페이지로 이동
    @GetMapping("/login")
    public String login(@Valid UserCreateForm userCreateForm, Model model){

        model.addAttribute("check", true);

        return "user/user_form";
    }

    //회원 정보 수정 홈페이지로 이동
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify")
    public String memberModify(@Valid UserModifyForm userModifyForm, Principal principal){

        User user = this.userService.getUser(principal.getName());

        userModifyForm.setId(user.getId());
        userModifyForm.setNickname(user.getNickname());

        return "user/user_modify";
    }

    //회원 정보 수정
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify")
    public String memberModify(@Valid UserModifyForm userModifyForm, Principal principal, BindingResult bindingResult){

        if(bindingResult.hasErrors())
            return "redirect:/";

        User user = this.userService.getUser(principal.getName());

        this.userService.modifyUser(user, userModifyForm.getId(), null,userModifyForm.getNickname());

        return "redirect:/user/logout"; //회원 정보 수정 후 로그아웃
    }

    //회원 탈퇴 기능
    @GetMapping("/withdrawal")
    public String withdrawal(Principal principal){

        User user = this.userService.getUser(principal.getName());  //현재 접속한 유저 정보를 가져옴

        this.userService.delete(user);

        return "redirect:/user/logout"; //회원 탈퇴 후 로그아웃
    }

}
