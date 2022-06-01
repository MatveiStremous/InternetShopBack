package com.example.backend.controllers;

import com.example.backend.models.Order;
import com.example.backend.requests.OrderRequest;
import com.example.backend.requests.UserRequest;
import com.example.backend.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("getAllOrders")
    public List<Order> getAllOrders(){
        return  orderService.getAllOrders();
    }

    @PostMapping("downOrderStatus/{id}")
    public void downOrderStatus(@PathVariable Long id){
        orderService.downOrderStatus(id);
    }

    @PostMapping("upOrderStatus/{id}")
    public void upOrderStatus(@PathVariable Long id){
        orderService.upOrderStatus(id);
    }

    @GetMapping("getUserOrders/{user_id}")
    public List<Order> getUserOrders(@PathVariable Long user_id){
        return  orderService.getOrdersByUserId(user_id);
    }

    @PostMapping("completeOrder/{id}")
    public void completeOrder(@PathVariable Long id){
        orderService.completeOrder(id);
    }

}

