package com.example.demo_jwt_security.Services;


import com.example.demo_jwt_security.DTO.LoginDTO;
import com.example.demo_jwt_security.DTO.UserDTO;
import com.example.demo_jwt_security.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserServiceImplement {
    @Autowired
    @Lazy
    private AuthenticationManager authenticationManager;

    @Autowired
    @Lazy
    private JwtService jwtService;

    private final List<User> users;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public UserServiceImplement(){
        users = new ArrayList<>();
    }

    public List<User> getAllUser(){
        return users;
    }




    public User findUserByName(String userName){
        for(User user : users){
            if(user.getUsername().trim().equalsIgnoreCase(userName.trim())){
                return user;
            }
        }

        return null;
    }


    public String loginUser(LoginDTO loginDTO){
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDTO.getUserName(),
                            loginDTO.getPassWord()
                    )
            );
            // Nếu không ném exception, chứng tỏ đăng nhập thành công
            return jwtService.generateToken(loginDTO.getUserName());
        } catch (AuthenticationException e) {
            return e.getMessage();
        }
    }


    public String createUser(UserDTO userDTO){
       try{
           User user = new User();
           user.setUserName(userDTO.getUserName());
           user.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassWord()));
           user.setStatus(true);
           user.setRole("User");
           users.add(user);
           return "User create successfull";
       }catch (Exception e){
           return e.getMessage();
       }
    }
}
