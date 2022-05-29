package com.example.backend.services;

import com.example.backend.models.Cart;
import com.example.backend.repositories.CartRepository;
import com.example.backend.repositories.ProductRepository;
import com.example.backend.repositories.UserRepository;
import com.example.backend.requests.CartRequest;
import com.example.backend.requests.ProductRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public void saveCartItem(CartRequest cartRequest) {
        Cart cartItem = new Cart();
        cartItem.setQuantity(cartRequest.getQuantity());
        cartItem.setProduct(productRepository.findById(cartRequest.getProductId()).orElseThrow(EntityNotFoundException::new));
        cartItem.setUser(userRepository.findById(cartRequest.getUserId()).orElseThrow(EntityNotFoundException::new));
        cartRepository.save(cartItem);
    }

    public void deleteCartItem(CartRequest cartRequest) {
        ArrayList<Cart> userCarts = new ArrayList<>(cartRepository.findByUserId(cartRequest.getUserId()));

        for (int i = 0; i < userCarts.size(); i++) {
            if(userCarts.get(i).getProduct().getId().equals(cartRequest.getProductId()))
                cartRepository.deleteById(userCarts.get(i).getId());
        }
    }

    public List<ProductRequest> getCartItemsByUserId(Long userId) {
        ArrayList<Cart> userCarts = new ArrayList<>(cartRepository.findByUserId(userId));

        List<ProductRequest> productRequestList = new ArrayList<>();

        for (int i = 0; i < userCarts.size(); i++) {
            ProductRequest tempProduct = new ProductRequest();
            tempProduct.setTitle(userCarts.get(i).getProduct().getTitle());
            tempProduct.setPrice(userCarts.get(i).getProduct().getPrice());
            tempProduct.setImageUrl(userCarts.get(i).getProduct().getImageUrl());
            tempProduct.setProductId(userCarts.get(i).getProduct().getId());
            tempProduct.setQuantity(userCarts.get(i).getQuantity());
            productRequestList.add(tempProduct);
        }
        return productRequestList;
    }

    public void updateQuantity(CartRequest cartRequest) {
        ArrayList<Cart> userCarts = new ArrayList<>(cartRepository.findByUserId(cartRequest.getUserId()));
        long index = 0;
        for (int i = 0; i < userCarts.size(); i++) {
            if(userCarts.get(i).getProduct().getId().equals(cartRequest.getProductId())) {
                index=userCarts.get(i).getId();
            }
        }
        Cart temp = cartRepository.findById(index).orElseThrow();

        temp.setQuantity(cartRequest.getQuantity());
        cartRepository.save(temp);

    }
}
