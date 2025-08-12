package com.samnang.user.controller;

import com.samnang.user.model.User;
import com.samnang.user.dto.UserRequest;
import com.samnang.user.dto.UserResponse;
import com.samnang.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> users = userService.fetchAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable String id) {
        return userService.fetchUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<String> addUser(@RequestBody UserRequest userRequest) {
        userService.addNewUser(userRequest);
        return ResponseEntity.ok("User added successfully");
    }

    @PutMapping("{id}")
    public ResponseEntity<String> updateUser(@PathVariable String id,@RequestBody UserRequest updateUserRequest) {
        boolean updated =  userService.updateUser(id,updateUserRequest);
        if(updated)
            return ResponseEntity.ok("User updated successfully");

        return ResponseEntity.notFound().build();

    }
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteUser(@PathVariable String id) {
        boolean delete = userService.deleteUserById(id);
        return delete ? ResponseEntity.ok("User removed successfully") : ResponseEntity.notFound().build();
    }
}
