package com.example.demo.Controller;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello(){
        return "Welcome to spring security demo???" + "status: " + HttpStatus.OK;
    }

    @GetMapping("/goodbye")
    public String goodBye(){
        return "Good bye";
    }
}
