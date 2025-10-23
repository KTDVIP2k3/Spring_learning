package Services;


import DTO.LoginDTO;
import DTO.UserDTO;
import Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserServiceImplement {
    @Autowired
    private AuthenticationManager authenticationManager;

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
            if(user.getUsername().equalsIgnoreCase(userName)){
                return user;
            }
        }

        return null;
    }


    public String loginUser(LoginDTO loginDTO){
        for(User user : users){
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUserName(), loginDTO.getPassWord()));

            if(authentication.isAuthenticated()){
                return "Login successfully";
            }
        }
        return "Login fail";
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
