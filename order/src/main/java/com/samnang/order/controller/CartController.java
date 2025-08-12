package com.samnang.order.controller;

import com.samnang.order.dto.CartItemRequest;
import com.samnang.order.model.CartItem;
import com.samnang.order.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @PostMapping
    public ResponseEntity<String> addToCart(
            @RequestHeader("X-User-ID") String userId,
            @RequestBody CartItemRequest request){
        boolean cart = cartService.addToCart(userId,request);
        if (!cart){
            return ResponseEntity.badRequest().body("Product Out Of Stock or Already exits");
        } else {
           return ResponseEntity.status(HttpStatus.CREATED).build();
        }
    }

    @DeleteMapping("/items/{productId}")
    public ResponseEntity<String> removeFromCart(
            @RequestHeader("X-User-ID") String userId,
            @PathVariable String productId){
        boolean deleteItemFromCart = cartService.deleteItemFromCart(userId,productId);

        return deleteItemFromCart ? ResponseEntity.noContent().build() : ResponseEntity.badRequest().build();

    }
    @GetMapping
    public ResponseEntity<List<CartItem>> getCartItem(@RequestHeader("X-User-ID") String userId){
        List<CartItem> cartItems = cartService.getCart(userId);
        return ResponseEntity.ok(cartItems);
    }

}
