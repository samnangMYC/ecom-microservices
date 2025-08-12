package com.samnang.order.service;

import com.samnang.order.dto.OrderItemDTO;
import com.samnang.order.dto.OrderResponse;
import com.samnang.order.dto.OrderStatus;
import com.samnang.order.model.CartItem;
import com.samnang.order.model.Order;
import com.samnang.order.model.OrderItem;
import com.samnang.order.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final CartService cartService;
   // private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    @Override
    public Optional<OrderResponse> placeNewOrder(String userId) {
        // Validate for cart items
        List<CartItem> cartItems = cartService.getCart(userId);
        if(cartItems.isEmpty()){
            return Optional.empty();
        }

        // Calculate total price
        BigDecimal totalPrice = cartItems.stream()
                .map(CartItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Create order
        Order order = new Order();
        order.setUserId(Long.valueOf(userId));
        order.setStatus(OrderStatus.COMPLETED);
        order.setTotalAmount(totalPrice);

        List<OrderItem> orderItems = cartItems.stream()
                .map(item -> new
                    OrderItem(null,
                    item.getProductId(),
                    item.getQuantity(),
                    item.getPrice(),
                    order
        )).toList();
        order.setItems(orderItems);

        Order savedOrder = orderRepository.save(order);

        // Clear cart
        cartService.clearCart(userId);

        return Optional.of(mapToOrderResponse(savedOrder)) ;
    }

    private OrderResponse mapToOrderResponse(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getTotalAmount(),
                order.getStatus(),
                order.getItems().stream()
                        .map(orderItem -> new OrderItemDTO(
                                orderItem.getId(),
                                orderItem.getProductId(),
                                orderItem.getQuantity(),
                                orderItem.getPrice(),
                                orderItem.getPrice().multiply(BigDecimal.valueOf(orderItem.getQuantity()))
                        ))
                        .toList(),
                order.getCreatedAt()
        );
    }

}
