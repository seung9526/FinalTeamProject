package com.springboot.Teamproject.service;

import com.springboot.Teamproject.UserRole;
import com.springboot.Teamproject.entity.BlogBoard;
import com.springboot.Teamproject.entity.ImageFile;
import com.springboot.Teamproject.entity.User;
import com.springboot.Teamproject.repository.BlogBoardRepository;
import com.springboot.Teamproject.repository.ImageFileRepository;
import com.springboot.Teamproject.repository.UserRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class BlogBoardService {

    private final BlogBoardRepository boardRepository;

    private final ImageFileRepository imageFileRepository;

    private final UserRepository userRepository;

    private String path = System.getProperty("user.dir");       //현재 프로젝트 절대 경로
    private String fullPath = path.replace("\\","/") + "/src/main/resources/static/img/";   //업로드 할 절대 경로

    //게시판 글 생성
    public void create(String title, String content,  MultipartFile files,User user) throws IOException {

        BlogBoard board = new BlogBoard();
        board.setTitle(title);
        board.setContent(content);
        board.setWriter(user.getNickname());
        board.setCreateDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yy-MM-dd HH:mm:ss")));
        board.setUserprofile(user);

        this.boardRepository.save(board);

        if(!files.isEmpty())        //이미지 파일이 있는지 확인, 있을 경우에만 파일 정보 등록
        {
            String origName = files.getOriginalFilename();
            String uuid = UUID.randomUUID().toString();
            String extension = origName.substring(origName.lastIndexOf("."));
            String savedName = uuid + extension;
            String savedPath = fullPath + savedName;

            File _file = new File(savedPath);

            //해당 경로의 폴더가 없을 경우 자동으로 폴더 생성 후 정상 작동
            if(_file.mkdirs())
                files.transferTo(_file);
            else
                files.transferTo(_file);

            ImageFile file = new ImageFile();
            file.setOriginName(origName);
            file.setSavedName(savedName);
            file.setSavedPath(savedPath);
            file.setBoard(board);

            this.imageFileRepository.save(file);
        }
    }

    //접속한 유저의 정보를 바탕으로 게시판 번호 최신순으로 게시판 글 목록을 가져옴
    public Page<BlogBoard> getList(String user_id, int pageNumber, int pageSize){

        User user = this.userRepository.getById(user_id);

        Sort sort = Sort.by("bno").descending();

        return this.boardRepository.findAllByUserprofile(user, PageRequest.of(pageNumber,pageSize,sort));
    }

    //해당 번호의 게시판을 직접 가져옴
    public BlogBoard getBlog(int bno){
        Optional<BlogBoard> board = this.boardRepository.findById(bno);

        return board.get();
    }

    //게시판 수정
    public void modifyBlog(BlogBoard board, String title, String content, MultipartFile file) throws IOException {

        List<ImageFile> beforeFiles = this.imageFileRepository.findAllByboardBno(board.getBno());

        board.setTitle(title);
        board.setContent(content);

        //기존 게시글에 이미지파일이 있는 경우
        if(!beforeFiles.isEmpty())
        {
            //기존 게시글의 이미지 파일이 변경 된 경우 새로운 이미지파일 업로드
            if(!file.isEmpty())
            {
                this.imageFileRepository.delete(beforeFiles.get(0));

                String origName = file.getOriginalFilename();
                String uuid = UUID.randomUUID().toString();
                String extension = origName.substring(origName.lastIndexOf("."));
                String savedName = uuid + extension;
                String savedPath = fullPath + savedName;

                File _file = new File(savedPath);

                //해당 경로의 폴더가 없을 경우 자동으로 폴더 생성 후 정상 작동
                if(_file.mkdirs())
                    file.transferTo(_file);
                else
                    file.transferTo(_file);

                ImageFile imageFile = new ImageFile();
                imageFile.setOriginName(origName);
                imageFile.setSavedName(savedName);
                imageFile.setSavedPath(savedPath);
                imageFile.setBoard(board);

                this.imageFileRepository.save(imageFile);
            }
        }else { //기존 게시글에 이미지가 없을 경우
            //수정된 게시글에 이미지가 업로드 될 경우
            if(!file.isEmpty()){

                String origName = file.getOriginalFilename();
                String uuid = UUID.randomUUID().toString();
                String extension = origName.substring(origName.lastIndexOf("."));
                String savedName = uuid + extension;
                String savedPath = fullPath + savedName;

                File _file = new File(savedPath);

                //해당 경로의 폴더가 없을 경우 자동으로 폴더 생성 후 정상 작동
                if(_file.mkdirs())
                    file.transferTo(_file);
                else
                    file.transferTo(_file);

                ImageFile imageFile = new ImageFile();
                imageFile.setOriginName(origName);
                imageFile.setSavedName(savedName);
                imageFile.setSavedPath(savedPath);
                imageFile.setBoard(board);

                this.imageFileRepository.save(imageFile);
            }
        }

        this.boardRepository.save(board);
    }

    //게시판 삭제
    public void deleteBlog(BlogBoard board){

        List<ImageFile> fileList = this.imageFileRepository.findAllByboardBno(board.getBno());
        File file;

        if(!fileList.isEmpty())
        {
            file = new File(this.imageFileRepository.getById(fileList.get(0).getFno()).getSavedPath());

            file.delete();
        }

        this.boardRepository.delete(board);
    }

    //제목 검색 기능
    public Page<BlogBoard> getSearchBoardList(String user_id , String search, int pageNumber, int pageSize){

        User user = this.userRepository.getById(user_id);

        Sort sort = Sort.by("bno").descending();

        return this.boardRepository.findAllByUserprofileAndTitleContaining(user,search, PageRequest.of(pageNumber,pageSize,sort));
    }
}
