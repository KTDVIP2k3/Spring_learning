    package com.example.demo.Service;

    import com.example.demo.Configure.SecurityConfigure;
    import com.example.demo.Model.User;
    import com.example.demo.Request.LoginDTO;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
    import org.springframework.security.web.SecurityFilterChain;
    import org.springframework.stereotype.Component;

    import java.util.ArrayList;
    import java.util.List;


    @Component
    public class UserServiceImplement {
        private final List<User> userList;


        public UserServiceImplement(){
            userList = new ArrayList<>();;
        }

        public List<User> getUserList(){
            return  this.userList;
        }

        public List<User> getAllUser(){
            return userList;
        }

        public String createUser(LoginDTO loginDTO){

            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

            if(loginDTO.getUserName() == null){
                return "User name can not be null";
            }
            if(loginDTO.getPassword() == null){
                return "User password can not be null";
            }

            User user = new User();
            user.setUserName(loginDTO.getUserName());
            user.setPassword(passwordEncoder.encode(loginDTO.getPassword()));
            user.setStatus(true);
            user.setRole("ROLE_USER");
            try{
                userList.add(user);
            }catch (Exception e){
                return e.getMessage();
            }
            return "Create user succesfully";
        }
    }
