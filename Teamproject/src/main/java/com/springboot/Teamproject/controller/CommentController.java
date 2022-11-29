package com.springboot.Teamproject.controller;

import com.springboot.Teamproject.DTO.CommentCreateForm;
import com.springboot.Teamproject.entity.User;
import com.springboot.Teamproject.service.CommentService;
import com.springboot.Teamproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    private final UserService userService;

    //댓글 생성 기능
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String commentCreate(@Valid CommentCreateForm commentCreateForm, Principal principal){

        User user = this.userService.getUser(principal.getName());

        this.commentService.create(commentCreateForm.getComment(),user,commentCreateForm.getBno());

        return "redirect:/blog/detail/"+commentCreateForm.getBno();
    }


    //댓글 삭제 기능
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete")
    public String commentDelete(@RequestParam int cno, @RequestParam int bno , Principal principal){

        this.commentService.delete(cno);

        return "redirect:/blog/detail/"+bno;
    }
}
