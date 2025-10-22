package com.example.demo.Controller;


import com.example.demo.Configure.SecurityConfigure;
import com.example.demo.Request.LoginDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
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
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO, HttpServletRequest request) {
        try {
            // ... (Bỏ dòng BCryptPasswordEncoder)

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDTO.getUserName(),
                            loginDTO.getPassword()
                    )
            );

            // Nếu không ném Exception, xác thực thành công
            if (authentication.isAuthenticated()) {
                SecurityContext context = SecurityContextHolder.createEmptyContext();
                context.setAuthentication(authentication);
                SecurityContextHolder.setContext(context);
                HttpSession session = request.getSession(true);
                session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,context);

                // Dùng ResponseEntity.ok() để đảm bảo Response được xử lý đúng chuẩn
                // và cho phép Session Filter thêm header Set-Cookie.
                return ResponseEntity.ok("Login succesfully. Session created.");
            }

            // Dòng này hiếm khi chạy
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login fail");

        } catch (AuthenticationException e) {
            // BẮT BUỘC phải bắt lỗi xác thực (nếu không sẽ là 500 Server Error)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials.");
        }
    }
}
