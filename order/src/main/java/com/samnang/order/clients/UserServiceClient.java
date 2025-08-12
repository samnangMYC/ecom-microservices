package com.samnang.order.clients;

import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange
public interface UserServiceClient {

    @GetExchange("/api/user")
    String getUserDetails(@RequestHeader("X-User-ID") String userId);

}
