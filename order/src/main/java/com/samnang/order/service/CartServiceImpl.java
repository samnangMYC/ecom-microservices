package com.samnang.order.service;

import com.samnang.order.clients.ProductServiceClient;
import com.samnang.order.dto.CartItemRequest;
import com.samnang.order.dto.ProductResponse;
import com.samnang.order.model.CartItem;
import com.samnang.order.repositories.CartItemRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CartServiceImpl implements CartService {

    private final CartItemRepository cartItemRepository;
    private final ProductServiceClient productServiceClient;

    @Override
    public boolean addToCart(String userId, CartItemRequest request) {

        ProductResponse productResponse = productServiceClient.getProductDetails(request.getProductId());


        if(productResponse == null || productResponse.getStockQty() < request.getQuantity() ){
            return false;
        }


        CartItem existingCartItem = cartItemRepository.findByUserIdAndProductId(userId,request.getProductId());

        BigDecimal unitPrice = new BigDecimal(0);

        if(existingCartItem != null){
            // Update the qty and price
            int qty = existingCartItem.getQuantity();
            existingCartItem.setQuantity(existingCartItem.getQuantity() + request.getQuantity());
            existingCartItem.setPrice(unitPrice.multiply(BigDecimal.valueOf(qty)));
            cartItemRepository.save(existingCartItem);
        } else {
            // Create nw cart item
            CartItem cartItem = new CartItem();
            cartItem.setProductId(request.getProductId());
            cartItem.setUserId(userId);
            cartItem.setQuantity(request.getQuantity());
            cartItem.setQuantity(request.getQuantity());
            cartItem.setPrice(BigDecimal.valueOf(1000.00));
            cartItemRepository.save(cartItem);
        }
    return true;
    }

    @Override
    public boolean deleteItemFromCart(String userId, String productId) {
       CartItem cartItem = cartItemRepository.findByUserIdAndProductId(userId,productId);
        if (cartItem != null){
            cartItemRepository.delete(cartItem);
            return true;
        }
        return false;

    }

    @Override
    public List<CartItem> getCart(String userId) {
        return cartItemRepository.findByUserId(userId);
    }

    @Override
    public void clearCart(String userId) {
        cartItemRepository.deleteByUserId(userId);
    }


}
