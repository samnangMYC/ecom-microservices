package com.samnang.user.service.impl;

import com.samnang.user.model.Address;
import com.samnang.user.model.User;
import com.samnang.user.repositories.UserRepository;
import com.samnang.user.dto.AddressDTO;
import com.samnang.user.dto.UserRequest;
import com.samnang.user.dto.UserResponse;
import com.samnang.user.service.KeyCloakAdminService;
import com.samnang.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final KeyCloakAdminService keyCloakAdminService;

    @Override
    public List<UserResponse> fetchAllUsers() {
        return userRepository.findAll().stream()
                .map(this::mapToUserResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void addNewUser(UserRequest userRequest) {
        String token = keyCloakAdminService.getAdminAccessToken();
        String keycloakUserId =
                keyCloakAdminService.createUser(token, userRequest);

        User newUser = new User();
        updateUserFromRequest(newUser,userRequest);
        newUser.setKeycloackId(keycloakUserId);

        keyCloakAdminService.assignRealmRoleToUser(userRequest.getUsername(),"USER",keycloakUserId);
        userRepository.save(newUser);
    }

    @Override
    public Optional<User> fetchUserById(String id) {
        return userRepository.findById(id);
    }

    @Override
    public boolean updateUser(String id, UserRequest updatedUserRequest) {
        return userRepository.findById(id)
                .map(existingUser -> {
                    updateUserFromRequest(existingUser,updatedUserRequest);
                    userRepository.save(existingUser);
                    return true;
                }).orElse(false);
    }

    @Override
    public boolean deleteUserById(String id) {
        if(userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private UserResponse mapToUserResponse(User user){
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setKeycloakId(user.getKeycloackId());
        userResponse.setUsername(user.getUsername());
        userResponse.setEmail(user.getEmail());
        userResponse.setPassword(user.getPassword());
        userResponse.setPhone(user.getPhone());
        userResponse.setRole(user.getRole());
        if(user.getAddress() != null){
            AddressDTO address = new AddressDTO();
            address.setStreet(user.getAddress().getStreet());
            address.setCity(user.getAddress().getCity());
            address.setState(user.getAddress().getState());
            address.setCode(user.getAddress().getCode());
            address.setCountry(user.getAddress().getCountry());
            userResponse.setAddress(address);
        }
        return userResponse;
    }
    private void updateUserFromRequest(User user,UserRequest userRequest){
        user.setUsername(userRequest.getUsername());
        user.setEmail(userRequest.getEmail());
        user.setPassword(userRequest.getPassword());
        user.setPhone(userRequest.getPhone());

        if (userRequest.getAddress() != null){
            Address address = new Address();
            address.setCity(userRequest.getAddress().getCity());
            address.setState(userRequest.getAddress().getState());
            address.setCode(userRequest.getAddress().getCode());
            address.setCountry(userRequest.getAddress().getCountry());
            address.setStreet(userRequest.getAddress().getStreet());
            user.setAddress(address);
        }
    }
}
