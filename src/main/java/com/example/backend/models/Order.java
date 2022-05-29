package com.example.backend.models;

import com.example.backend.models.enums.OrderStatus;
import com.example.backend.models.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private Long id;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    private User user;

    @Column(name="content", columnDefinition = "text")
    private String content;

    @Column(name="totalPrice")
    private float totalPrice;

    @Column(name="dateOfCreation")
    private LocalDateTime dateOfCreation;

    @Column(name="comment",columnDefinition = "text")
    private String comment;

    @Column(name="address")
    private String address;

    @Column(name="phoneNumber")
    private String phoneNumber;

    @PrePersist
    private void init(){
        dateOfCreation=LocalDateTime.now();
    }

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
}

