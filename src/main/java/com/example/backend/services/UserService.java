package com.example.backend.services;

import com.example.backend.models.User;
import com.example.backend.models.enums.Role;
import com.example.backend.repositories.UserRepository;
import com.example.backend.requests.LoginRequest;
import com.example.backend.requests.UserRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public boolean createUser(User user) {
        String email = user.getEmail();
        if (userRepository.findByEmail(email) != null) return false;
        user.setActive(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER_ROLE);//getRole().add(Role.USER_ROLE);
        userRepository.save(user);
        log.info("Saving new User with email: {}", email);
        return true;
    }

    public UserRequest logInUser(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail());

        if (passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            UserRequest userRequest = new UserRequest();
            userRequest.setEmail(user.getEmail());
            userRequest.setName(user.getName());
            userRequest.setRole(user.getRole());
            userRequest.setActive(user.isActive());
            userRequest.setId(user.getId());
            return userRequest;
        }
        return null;
    }

    public UserRequest getUserInfoById(Long id) {
        UserRequest userRequest = new UserRequest();
        User user = userRepository.findById(id).orElseThrow();
        userRequest.setEmail(user.getEmail());
        userRequest.setName(user.getName());
        userRequest.setRole(user.getRole());
        userRequest.setActive(user.isActive());
        userRequest.setId(user.getId());

        return userRequest;
    }

    public List<UserRequest> getAllUsers() {
        List<UserRequest> userRequests = new ArrayList<>();
        List<User> users = new ArrayList<>(userRepository.findAll());
        long size = users.size();
        for (int i = 0; i < size; i++) {
            UserRequest userRequest = new UserRequest();
            userRequest.setId(users.get(i).getId());
            userRequest.setEmail(users.get(i).getEmail());
            userRequest.setName(users.get(i).getName());
            userRequest.setRole(users.get(i).getRole());
            userRequest.setActive(users.get(i).isEnabled());

            userRequests.add(userRequest);
        }

        return userRequests;
    }

    public void downUserStatus(Long id) {
        User user = userRepository.findById(id).orElseThrow();
        if (user.getRole().equals(Role.USER_ROLE)) {
            user.setActive(false);
        } else if (user.getRole().equals(Role.MODERATOR_ROLE)) {
            user.setRole(Role.USER_ROLE);
        } else if (user.getRole().equals(Role.ADMIN_ROLE)) {
            user.setRole(Role.MODERATOR_ROLE);
        }
        userRepository.save(user);
    }

    public void upUserStatus(Long id) {
        User user = userRepository.findById(id).orElseThrow();
        if (user.getRole().equals(Role.USER_ROLE) && user.isEnabled()) {
            user.setRole(Role.MODERATOR_ROLE);
        } else if (user.getRole().equals(Role.MODERATOR_ROLE)) {
            user.setRole(Role.ADMIN_ROLE);
        } else if (!user.isEnabled()) {
            user.setActive(true);
        }
        userRepository.save(user);
    }
}
