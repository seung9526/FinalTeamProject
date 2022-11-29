package com.springboot.Teamproject.service;

import com.springboot.Teamproject.entity.Cart;
import com.springboot.Teamproject.entity.Product;
import com.springboot.Teamproject.entity.Purchase;
import com.springboot.Teamproject.entity.User;
import com.springboot.Teamproject.repository.CartRepository;
import com.springboot.Teamproject.repository.ProductRepository;
import com.springboot.Teamproject.repository.PurchaseRepository;
import com.springboot.Teamproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;

    private final CartRepository cartRepository;

    private final ProductRepository productRepository;

    private final UserRepository userRepository;

    /* 구매 */
    public void saveBuy(int cartNumber){

        // 카트번호를 담은 findById를 get으로 cart에 담음
        Cart cart = this.cartRepository.findById(cartNumber).get();

        // findById에 카트 제품, 제품번호 데이터를 가져와 product에 담음
        Product product = this.productRepository.findById(cart.getProduct().getPno()).get();

        // findById에 카트 사용자정보와 사용자 아이디를 불러와 user에 담음
        User user = this.userRepository.findById(cart.getUserprofile().getId()).get();

        // 구매 생성자를 만든 뒤 생성자에 product, user, cart.getProductCount()[제품 갯수], 구매 시간을 을 담음
        Purchase purchase = new Purchase();
        purchase.setProduct(product);
        purchase.setUserprofile(user);
        purchase.setProductCount(cart.getProductCount());
        purchase.setDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yy-MM-dd HH:mm:ss")));

        this.purchaseRepository.save(purchase);
    }

    /* 구매 리스트 */
    public List<Purchase> getList(String user_id){

        // 유저아이디를 findById로 가져와 user에 담음
        User user = this.userRepository.findById(user_id).get();

        return this.purchaseRepository.findAllByUserprofileOrderByPurchaseNumberDesc(user);
    }
}

