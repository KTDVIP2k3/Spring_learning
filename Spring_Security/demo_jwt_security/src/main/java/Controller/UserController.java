package Controller;

import DTO.LoginDTO;
import DTO.UserDTO;
import Model.User;
import Services.UserServiceImplement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

    @PostMapping("/login")
    public String login(LoginDTO loginDTO){
        return userServiceImplement.loginUser(loginDTO);
    }

    @PostMapping("/createUser")
    public String createUser(UserDTO userDTO){
        return userServiceImplement.createUser(userDTO);
    }
}
