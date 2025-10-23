package com.example.demo_jwt_security.Services;

import com.example.demo_jwt_security.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailServiceImplement implements UserDetailsService {
    @Autowired
    @Lazy
    UserServiceImplement userServiceImplement;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        for(User user : userServiceImplement.getAllUser()){
            if(!user.getUsername().equalsIgnoreCase(username)){
                return user;
            }
        }
        return null;
    }
}
