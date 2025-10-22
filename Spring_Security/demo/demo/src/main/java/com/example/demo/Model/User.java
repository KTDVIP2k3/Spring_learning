package com.example.demo.Model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;


public class User implements UserDetails {
    private String userName;
    private String password;
    private boolean status;
    private String role;

    public User() {
    }

    public void setRole(String role){
        this.role = role;
    }

    public User(String userName, String password, boolean status) {
        this.userName = userName;
        this.password = password;
        this.status = status;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isStatus() {
        return status;
    }



    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        if (this.role == null) {
            return Collections.emptyList(); // Trả về danh sách rỗng nếu không có role
        }

        // SỬA LỖI: Tạo một đối tượng SimpleGrantedAuthority từ String role
        // và trả về dưới dạng một danh sách (Collection)
        return Collections.singletonList(new SimpleGrantedAuthority(this.role));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.userName;
    }

    @Override
    public boolean isEnabled() {
        return this.status;
    }
}
