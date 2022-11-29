package com.springboot.Teamproject.controller;

import com.springboot.Teamproject.entity.Cart;
import com.springboot.Teamproject.entity.Purchase;
import com.springboot.Teamproject.entity.User;
import com.springboot.Teamproject.service.CartService;
import com.springboot.Teamproject.service.PurchaseService;
import com.springboot.Teamproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/purchase")
public class PurchaseController {

    // 의존성 주입
    private final PurchaseService purchaseService;
    private final CartService cartService;
    private final UserService userService;

    // 제품 구매 후 장바구니 삭제
    @GetMapping("/buy")
    public String purchaseMain(Principal principal, Model model){

        // 사용자 정보를 가져와 user에 담음
        User user = userService.getUser(principal.getName());

        // 아이디 정보를 기반으로 유저 정보를 가져와 cartList에 담음
        List<Cart> cartList = cartService.getUserCart(user);

        // for each 문으로 cart에 getNumber=구매번호 를 조회 후 purchase에 저장 한뒤 카트에 저장된 데이터 삭제
        for(Cart cart : cartList){
            this.purchaseService.saveBuy(cart.getNumber());
            this.cartService.deleteCart(cart.getNumber());
        }

        return "redirect:/product/list";
    }

    // 결제 리스트
    @GetMapping("/list")
    public String getPurchaseList(Principal principal, Model model){

        // 구매서비스에서 getList에 사용자이름을 불러와 리스트로 구매엔티티를 담음
        List<Purchase> getList =  this.purchaseService.getList(principal.getName());

        model.addAttribute("getList", getList);

        return "purchase/purchase_main";

    }


}
