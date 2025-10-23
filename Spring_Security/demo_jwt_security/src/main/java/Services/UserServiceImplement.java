package Services;


import DTO.LoginDTO;
import DTO.UserDTO;
import Model.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserServiceImplement {
    private final List<User> users;

    public UserServiceImplement(){
        users = new ArrayList<>();
    }

    public List<User> getAllUser(){
        return users;
    }


    public User findUserByName(String userName){
        for(User user : users){
            if(user.getUsername().equalsIgnoreCase(userName)){
                return user;
            }
        }

        return null;
    }


    public String createUser(UserDTO userDTO){
       try{
           BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
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
