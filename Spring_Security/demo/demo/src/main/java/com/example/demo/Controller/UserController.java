package com.example.demo.Controller;


import com.example.demo.Model.User;
import com.example.demo.Request.LoginDTO;
import com.example.demo.Service.UserServiceImplement;
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

    @PostMapping("/createUser")
    public String createUser(@RequestBody LoginDTO loginDTO){
        return userServiceImplement.createUser(loginDTO);
    }


}
