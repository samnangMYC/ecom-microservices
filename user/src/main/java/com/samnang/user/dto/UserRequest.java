package com.samnang.user.dto;

import lombok.Data;

@Data
public class UserRequest {
    private String username;
    private String email;
    private String phone;
    private String password;
    private AddressDTO address;
}
