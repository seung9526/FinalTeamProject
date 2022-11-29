package com.springboot.Teamproject.controller;

import com.springboot.Teamproject.DTO.BoardCreateForm;
import com.springboot.Teamproject.DTO.BoardSearchForm;
import com.springboot.Teamproject.DTO.CommentCreateForm;
import com.springboot.Teamproject.entity.BlogBoard;
import com.springboot.Teamproject.entity.Comment;
import com.springboot.Teamproject.entity.ImageFile;
import com.springboot.Teamproject.entity.User;
import com.springboot.Teamproject.service.BlogBoardService;
import com.springboot.Teamproject.service.CommentService;
import com.springboot.Teamproject.service.ImageFileService;
import com.springboot.Teamproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/blog")
public class BlogController {

    private final BlogBoardService boardService;

    private final UserService userService;

    private final CommentService commentService;

    private final ImageFileService imageFileService;

    //게시판 목록 기능
    @GetMapping("/list")
    public String getBlogBoardList(Model model , BoardSearchForm boardSearchForm, Principal principal ,
                                   @RequestParam(value = "page", defaultValue = "0") int pageNumber, @RequestParam(value = "size", defaultValue = "6") int pageSize){

        if(principal != null)
        {
            //게시판 블로그 글 페이지 목록
            Page<BlogBoard> blogBoardPage = this.boardService.getList(principal.getName(),pageNumber,pageSize);

            //페이지당 블로그 글 목록
            List<BlogBoard> blogBoardList = blogBoardPage.getContent();

            //블로그 게시글 페이지 연동
            model.addAttribute("pageList",blogBoardPage);

            //블로그 게시글 목록 연동
            model.addAttribute("blogBoardList",blogBoardList);

            return "blog/blog_main";     //해당 이름을 가진 홈페이지로 이동

        }else {
            return "redirect:/user/login";
        }
    }

    //게시글 목록에서 제목을 클릭했을 때 해당 번호의 게시글 정보를 가져오기 위한 매핑
    @GetMapping("/detail/{bno}")
    public String getBlogBoardDetail(Model model , @Valid CommentCreateForm commentCreateForm, @PathVariable("bno") int bno , Principal principal){

        //게시글 번호를 통해 정보를 받아온뒤 모델과 연동
        BlogBoard blogBoard = this.boardService.getBlog(bno);
        model.addAttribute("blogBoard", blogBoard);

        //게시글 번호를 통해 파일 정보 목록을 가져온 뒤 모델과 연동
        List<ImageFile> fileList = this.imageFileService.getImageFiles(bno);
        model.addAttribute("fileList", fileList);

        //게시글 번호를 통해 댓글 정보 목록을 가져온 뒤 모델과 연동
        List<Comment> commentList = this.commentService.getList(bno);
        model.addAttribute("commentList", commentList);

        return "blog/blog_detail";
    }

    //게시글 생성 홈페이지로 이동
    @PreAuthorize("isAuthenticated()")      //로그인 했는지 유무를 확인
    @GetMapping("/create")
    public String blogBoardCreate(BoardCreateForm boardCreateForm){
        return "blog/blog_create";
    }

    //게시글 등록 시 정보를 보내주는 기능
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String blogBoardCreate(@Valid BoardCreateForm boardCreateForm, BindingResult bindingResult,Principal principal) throws IOException {

        if(bindingResult.hasErrors())
            return "blog/blog_create";

        User user = this.userService.getUser(principal.getName());      //현재 로그인한 유저의 id를 가져와 유저 정보를 찾아옴
        this.boardService.create(boardCreateForm.getTitle(),boardCreateForm.getContent(),boardCreateForm.getFile(),user);

        return "redirect:/blog/list";
    }

    //게시글 수정 홈페이지로 이동
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{bno}")
    public String blogBoardModify(BoardCreateForm boardCreateForm,BindingResult bindingResult,@PathVariable Integer bno, Model model, Principal principal){

        BlogBoard board = this.boardService.getBlog(bno);
        List<ImageFile> fileList = this.imageFileService.getImageFiles(bno);

        model.addAttribute("board",board);

        if(!fileList.isEmpty())
            model.addAttribute("imageFile", fileList.get(0));
        else
            model.addAttribute("imageFile",null);

        //해당 게시글의 정보를 미리 등록
        boardCreateForm.setTitle(board.getTitle());
        boardCreateForm.setContent(board.getContent());

        return "blog/blog_modify";
    }

    //수정 된 게시글 등록 하는 기능
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{bno}")
    public String blogBoardModify(@Valid BoardCreateForm boardCreateForm,BindingResult bindingResult,@PathVariable Integer bno) throws IOException {

        if(bindingResult.hasErrors())
            return "redirect:/blog/modify/"+bno;

        BlogBoard board = this.boardService.getBlog(bno);

        this.boardService.modifyBlog(board,boardCreateForm.getTitle(),boardCreateForm.getContent(),boardCreateForm.getFile());

        return "redirect:/blog/detail/"+bno;
    }

    //게시글 수정 시 이미지 삭제 기능
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/imageDelete/{bno}")
    public String blogBoardImageDelete(@PathVariable Integer bno){

        this.imageFileService.delete(this.imageFileService.getImageFiles(bno).get(0).getFno());

        return "redirect:/blog/modify/"+bno;
    }

    //게시글 삭제 기능
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{bno}")
    public String blogBoardDelete(@PathVariable Integer bno){

        BlogBoard board = this.boardService.getBlog(bno);

        this.boardService.deleteBlog(board);

        return "redirect:/blog/list";
    }

    //게시글 검색 기능
    @PostMapping("/search")
    public String blogBoardSearch(Model model, @Valid BoardSearchForm boardSearchForm, BindingResult bindingResult, Principal principal ,
                                    @RequestParam(value = "page", defaultValue = "0") int pageNumber, @RequestParam(value = "size", defaultValue = "6") int pageSize){

        if(bindingResult.hasErrors())
            return "blog/blog_main";

        //해당 검색어가 포함된 제목의 게시글만 목록으로 가져옴
        Page<BlogBoard> blogBoardPage = this.boardService.getSearchBoardList(principal.getName(), boardSearchForm.getSearch(),pageNumber,pageSize);

        model.addAttribute("pageList",blogBoardPage);
        model.addAttribute("blogBoardList",blogBoardPage.getContent());

        return "blog/blog_main";     //해당 이름을 가진 홈페이지로 이동

    }
}
