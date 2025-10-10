package com.samnang.user.dto;

import com.samnang.user.model.UserRole;
import lombok.Data;

@Data
public class UserResponse {
    private String id;
    private String keycloakId;
    private String username;
    private String email;
    private String phone;
    private UserRole role;
    private String password;
    private AddressDTO address;
}
