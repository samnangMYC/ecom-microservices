package com.samnang.order.service;


import com.samnang.order.dto.OrderResponse;

import java.util.Optional;

public interface OrderService {

    Optional<OrderResponse> placeNewOrder(String userId);
}
