package com.samnang.order.service;


import com.samnang.order.dto.CartItemRequest;
import com.samnang.order.model.CartItem;

import java.util.List;

public interface CartService {
    boolean addToCart(String userId, CartItemRequest request);

    boolean deleteItemFromCart(String userId, String productId);

    List<CartItem> getCart(String userId);

    void clearCart(String userId);
}
