package com.example.backend.controllers;

import com.example.backend.requests.OrderRequest;
import com.example.backend.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
@CrossOrigin("*")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("formNewOrder")
    public String formNewOrder(@RequestBody OrderRequest orderRequest){
        orderService.formNewOrder(orderRequest);
        return "Your order is formed!";
    }


}

