package com.example.demo_jwt_security.Controller;

import com.example.demo_jwt_security.DTO.LoginDTO;
import com.example.demo_jwt_security.DTO.UserDTO;
import com.example.demo_jwt_security.Model.User;
import com.example.demo_jwt_security.Services.UserServiceImplement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    UserServiceImplement userServiceImplement;

    @GetMapping("/getAllUser")
    public List<User> getAllUser(){
        return userServiceImplement.getAllUser();
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginDTO loginDTO){
        return userServiceImplement.loginUser(loginDTO);
    }

    @PostMapping("/createUser")
    public String createUser(@RequestBody UserDTO userDTO){
        return userServiceImplement.createUser(userDTO);
    }
}
