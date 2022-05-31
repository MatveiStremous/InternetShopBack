package com.example.backend.controllers;

import com.example.backend.models.User;
import com.example.backend.requests.LoginRequest;
import com.example.backend.requests.ProductRequest;
import com.example.backend.requests.UserRequest;
import com.example.backend.response.Message;
import com.example.backend.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("registration")
    public boolean createUser(@RequestBody User user){
        return userService.createUser(user);
    }

    @PostMapping("login")
    public ResponseEntity<?> logIn(@RequestBody LoginRequest user){
        UserRequest userRequest = new UserRequest();
        userRequest = userService.logInUser(user);
        if(userRequest == null){
            return ResponseEntity.badRequest().body(new Message("Неверно введён логин или пароль"));
        }
        else
        {
            if(userService.isEmailConfirmed(user.getEmail())){
                return ResponseEntity.ok(userRequest);
            }
            return ResponseEntity.badRequest().body(new Message("Вы не подтвердили Вашу почту!"));
        }

    }

    @GetMapping("getFreshInfoAboutUser/{id}")
    public UserRequest getFreshInfoAboutUser(@PathVariable Long id){
        return userService.getUserInfoById(id);
    }

    @GetMapping("getAllUsers")
    public List<UserRequest> getAllUsers(){
        return  userService.getAllUsers();
    }

    @PostMapping("downStatus/{id}")
    public void downUserStatus(@PathVariable Long id){
        userService.downUserStatus(id);
    }

    @PostMapping("upStatus/{id}")
    public void upUserStatus(@PathVariable Long id){
        userService.upUserStatus(id);
    }

    @GetMapping("regitrationConfirm")
    public void confirmRegistration(@RequestParam("token") String token) {
        userService.confirmRegistration(token);
    }


}
