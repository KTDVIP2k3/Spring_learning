package com.example.demo.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Autowired
    AuthenticationManager authenticationManager;


    @GetMapping("/hello")
    public String hello(){
        return "Welcome to spring security demo???" + "status: " + HttpStatus.OK;
    }

    @GetMapping("/goodbye")
    public String goodBye(){
        return "Good bye";
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginDTO loginDTO){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getUserName(),
                        loginDTO.getPassword() // gửi raw password, Spring sẽ tự check với PasswordEncoder
                )
        );
        if(authentication.isAuthenticated()){
            return  "Login succesfully";
        }

        return "Login fail";
    }
}
