package com.springboot.Teamproject.controller;

import com.springboot.Teamproject.service.CartService;
import com.springboot.Teamproject.service.ProductService;
import com.springboot.Teamproject.entity.Cart;
import com.springboot.Teamproject.entity.Product;
import com.springboot.Teamproject.entity.User;
import com.springboot.Teamproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;
    private final UserService userService;
    private final ProductService productService;

    //장바구니목록
    @GetMapping("/list")
    public String main(Model model, Principal principal){

        if(principal != null)
        {
            User user = this.userService.getUser(principal.getName()); //현재 로그인정보 (userService 의 getUser Method)

            List<Cart> cart = this.cartService.getUserCart(user); //유저정보의 장바구니정보 가져오기 (cartService 의 getUserCart method)

            //전체금액 계산
            int totalPrice=0;

            for(int i=0; i<cart.size();i++){
                totalPrice += cart.get(i).getProduct().getPrice() * cart.get(i).getProductCount();
            }

            model.addAttribute("totalPrice" , totalPrice); //전체금액

            model.addAttribute("cartList", cart); //유저에 대한 카트정보

            return "cart/cart_main";
        }else {
            model.addAttribute("message", "로그인해야합니다");
            model.addAttribute("searchUrl", "/user/login");
            return "message";
        }
    }

    @GetMapping("/plus/{number}") //장바구니 상품갯수 +1
    public String plus(@PathVariable int number){
        Cart cart = this.cartService.getCart(number); //카트정보
        this.cartService.plusCart(cart); // + 1
        return "redirect:/cart/list";
    }

    @GetMapping("/minus/{number}") //장바구니 상품개수 -1
    public String minus(@PathVariable int number){
        Cart cart = this.cartService.getCart(number); //카트정보
        this.cartService.minusCart(cart); // -1
        return "redirect:/cart/list";
    }

    //장바구니 등록
    @GetMapping("/add/{product}")
    public String save(@PathVariable("product") int product_id, Principal principal, Model model){

        //로그인정보확인
        if(principal != null){
            Product product = this.productService.getProduct(product_id); //장바구니에 등록 (productService 의 getProduct method
            User user = this.userService.getUser(principal.getName()); //로그인된 유저정보 가져오기

            //중복제거
            if(this.cartService.isProduct(product_id , principal.getName())){ //cartService 의 isProduct 사용하여 중복확인
                System.out.println("이미등록된 상품"); //참일땐 이미등록된상품
                model.addAttribute("message", "이미 등록된 상품입니다.");
                model.addAttribute("searchUrl", "/cart/list");
                return "message";
            }else {
                this.cartService.savedCart(product, user); //중복이 없을 때 저장
                model.addAttribute("message", "장바구니 등록완료. 계속쇼핑하시겠습니까?");
                model.addAttribute("message1", "장바구니등록완료");
                model.addAttribute("searchUrl", "/product/list");
                model.addAttribute("canUrl", "/cart/list");

                return "select";
            }
        }else {
            //message 로 보내줄 경고문과 로그인 페이지로 보내줄 url
            model.addAttribute("message", "로그인해야합니다.");
            model.addAttribute("searchUrl", "/user/login");
            return "message";
        }

    }


    //장바구니 삭제
    @GetMapping("/delete/{number}")
    public String delete(@PathVariable int number, Model model){

        this.cartService.deleteCart(number); //카트 넘버로 가져와서 지우기
        model.addAttribute("message", "삭제되었습니다. ");
        model.addAttribute("searchUrl", "/cart/list");
        return "message";
    }


    @GetMapping("/buy") //구매전 확인 및 선택하는 페이지로 넘겨줌
    public String buyPage(Model model){


        //구매전 확인 html 로 메세지와  url 을 보내줌
        model.addAttribute("message", "구매하시겠습니까?");
        model.addAttribute("message1", "구매완료되었습니다.");
        model.addAttribute("searchUrl", "/purchase/buy");
        model.addAttribute("canUrl", "/cart/list");

        return "select";
    }
}
