package com.example.backend.services;

import com.example.backend.models.Cart;
import com.example.backend.models.Order;
import com.example.backend.models.enums.OrderStatus;
import com.example.backend.repositories.CartRepository;
import com.example.backend.repositories.OrderRepository;
import com.example.backend.repositories.UserRepository;
import com.example.backend.requests.OrderRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final UserRepository userRepository;

    public void formNewOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderStatus(OrderStatus.AWAITING);
        order.setAddress(orderRequest.getAddress());
        order.setComment(orderRequest.getComment());
        order.setPhoneNumber(orderRequest.getPhoneNumber());
        order.setUser(userRepository.findById(orderRequest.getUserId()).orElse(null));
        String content="";
        float totalPrice = 0;
        ArrayList<Cart> userCarts = new ArrayList<>(cartRepository.findByUserId(orderRequest.getUserId()));
        long size = userCarts.size();
        for (int i = 0; i < size; i++) {
            totalPrice=totalPrice+userCarts.get(i).getProduct().getPrice()*userCarts.get(i).getQuantity();
            content = content + "#"+ userCarts.get(i).getProduct().getId() + " "
                    + userCarts.get(i).getProduct().getTitle()+ " x " +userCarts.get(i).getQuantity()+"; ";
            cartRepository.deleteById(userCarts.get(i).getId());
        }
        order.setContent(content);
        order.setTotalPrice(totalPrice);

        orderRepository.save(order);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public List<Order> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    public void downOrderStatus(Long id) {
        Order order = orderRepository.findById(id).orElse(null);
        if(order.getOrderStatus().equals(OrderStatus.COMPLETED)){
            order.setOrderStatus(OrderStatus.SENT);
        }
        else if(order.getOrderStatus().equals(OrderStatus.SENT)){
            order.setOrderStatus(OrderStatus.ACCEPTED);
        }
        else if(order.getOrderStatus().equals(OrderStatus.ACCEPTED)){
            order.setOrderStatus(OrderStatus.AWAITING);
        }
        else if(order.getOrderStatus().equals(OrderStatus.AWAITING)){
            order.setOrderStatus(OrderStatus.CANCELED);
        }
        orderRepository.save(order);
    }

    public void upOrderStatus(Long id) {
        Order order = orderRepository.findById(id).orElse(null);
        if(order.getOrderStatus().equals(OrderStatus.CANCELED)){
            order.setOrderStatus(OrderStatus.AWAITING);
        }
        else if(order.getOrderStatus().equals(OrderStatus.AWAITING)){
            order.setOrderStatus(OrderStatus.ACCEPTED);
        }
        else if(order.getOrderStatus().equals(OrderStatus.ACCEPTED)){
            order.setOrderStatus(OrderStatus.SENT);
        }
        else if(order.getOrderStatus().equals(OrderStatus.SENT)){
            order.setOrderStatus(OrderStatus.COMPLETED);
        }
        orderRepository.save(order);
    }

    public void completeOrder(Long id) {
        Order order = orderRepository.findById(id).orElse(null);
        order.setOrderStatus(OrderStatus.COMPLETED);
        orderRepository.save(order);
    }
}
