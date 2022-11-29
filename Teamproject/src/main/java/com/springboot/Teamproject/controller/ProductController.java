package com.springboot.Teamproject.controller;

import com.springboot.Teamproject.DTO.ProductSearchForm;
import com.springboot.Teamproject.service.ProductService;
import com.springboot.Teamproject.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;

    //상품리스트
    @GetMapping("/list")
    //0페이지부터, 한화면에 게시글 12개, 상품번호기준으로 역순으로 정렬
    public String productMain(@Valid ProductSearchForm productSearchForm,Model model, @RequestParam(value = "page", defaultValue = "0")
    Integer pageNumber) {

        Page<Product> productPage = this.productService.getList(pageNumber);

        model.addAttribute("productPage",productPage);
        model.addAttribute("productList",productPage.getContent());

        return "product/main";
    }

    //카테고리별 페이지
    @GetMapping("/category/{code}")
    public String productCategory(@Valid ProductSearchForm productSearchForm, @PathVariable("code") String code,Model model,
                                  @RequestParam(value = "page", defaultValue = "0") Integer pageNumber) {

        Page<Product> categoryProduct = this.productService.category(code,pageNumber);

        model.addAttribute("categoryPage",categoryProduct);
        model.addAttribute("productList",categoryProduct.getContent());

        //카테고리 페이지 출력
        return "product/main";
    }


    //상품정보페이지
    @GetMapping("/view/{pno}")
    public String productView(@PathVariable("pno") int pno, Model model) {

        //제품번호를 변수로 받아와서 등록된 상품정보를 저장
        Product productList = this.productService.getProduct(pno);

        //모델에 상품정보를 넘겨준다
        model.addAttribute("productView", productList);

        //상품정보 페이지 출력
        return "product/detail";
    }


    //검색기능
    @PostMapping("/search")
    public String productSearchResult(@Valid ProductSearchForm productSearchForm, Pageable pageable, Model model) {
        Page<Product> list = productService.searchProduct(productSearchForm.getSearchKeyword(), pageable);

        model.addAttribute("productPage", list);
        model.addAttribute("productList", list.getContent());

        return "product/main";
    }

}