package com.example.demo.Service;

import com.example.demo.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomerDetailService implements UserDetailsService {

    @Autowired
    UserServiceImplement userServiceImplement;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        for(User user : userServiceImplement.getUserList()){
            if(user.getUserName().equalsIgnoreCase(username)){
                return user;
            }
        }
        throw new UsernameNotFoundException("User not found with username: " + username);
    }
}
