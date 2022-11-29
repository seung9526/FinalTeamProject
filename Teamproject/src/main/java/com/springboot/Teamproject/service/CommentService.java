package com.springboot.Teamproject.service;

import com.springboot.Teamproject.entity.BlogBoard;
import com.springboot.Teamproject.entity.Comment;
import com.springboot.Teamproject.entity.User;
import com.springboot.Teamproject.repository.BlogBoardRepository;
import com.springboot.Teamproject.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    private final BlogBoardRepository boardRepository;

    //댓글 생성
    public void create(String comment, User user, int bno){

        Comment _comment = new Comment();
        _comment.setComment(comment);
        _comment.setWriter(user.getNickname());
        _comment.setCreateDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yy-MM-dd HH:mm:ss")));
        _comment.setUserprofile(user);
        _comment.setBoard(this.boardRepository.findById(bno).get());

        this.commentRepository.save(_comment);
    }

    //해당 번호의 게시글에 등록된 댓글 목록 가져옴
    public List<Comment> getList(int bno){

        return this.commentRepository.findAllByboardBno(bno);
    }

    //댓글 삭제
    public void delete(int cno){

        this.commentRepository.deleteById(cno);
    }
}
