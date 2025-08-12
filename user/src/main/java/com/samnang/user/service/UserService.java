package com.samnang.user.service;

import com.samnang.user.model.User;
import com.samnang.user.dto.UserRequest;
import com.samnang.user.dto.UserResponse;

import java.util.List;
import java.util.Optional;

public interface UserService  {
    List<UserResponse> fetchAllUsers();

    void addNewUser(UserRequest userRequest);

    Optional<User> fetchUserById(String id);

    boolean updateUser(String id, UserRequest userRequest);

    boolean deleteUserById(String id);
}
