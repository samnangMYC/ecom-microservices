package com.samnang.order.controller;

import com.samnang.order.dto.OrderResponse;
import com.samnang.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@RequestHeader("X-User-ID") String userId){
        return orderService.placeNewOrder(userId)
                .map(orderResponse -> new ResponseEntity<>(orderResponse,HttpStatus.CREATED))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

}
