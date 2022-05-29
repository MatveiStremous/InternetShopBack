package com.example.backend.controllers;

import com.example.backend.requests.CartRequest;
import com.example.backend.requests.ProductRequest;
import com.example.backend.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
@CrossOrigin("*")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @PostMapping("addCartItem")
    public String addItemToCart(@RequestBody CartRequest cartRequest){
        cartService.saveCartItem(cartRequest);
        return "You added a new product to cart!";
    }

    @PostMapping("deleteCartItem")
    public String deleteItemFromCart(@RequestBody CartRequest cartRequest){
        cartService.deleteCartItem(cartRequest);
        return "You delete item from cart!";
    }

    @PostMapping("updateQuantity")
    public void updateQuantity(@RequestBody CartRequest cartRequest){
        cartService.updateQuantity(cartRequest);
    }

    @GetMapping("getCartItems/{id}")
    public List<ProductRequest> getCartItemsByUserId(@PathVariable Long id){
        return cartService.getCartItemsByUserId(id);
    }

}
