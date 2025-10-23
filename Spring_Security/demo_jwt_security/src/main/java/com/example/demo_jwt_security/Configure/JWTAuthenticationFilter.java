package com.example.demo_jwt_security.Configure;

import com.example.demo_jwt_security.Services.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    @Lazy
    private JwtService jwtService;

    @Autowired
    @Lazy
    private UserDetailsService userDetailsService;

    // Định nghĩa các đường dẫn công khai (cùng với các đường dẫn trong permitAll() của SecurityConfig)
    private static final List<String> PUBLIC_URIS = Arrays.asList(
            "/login",
            "/createUser",
            "/getAllUser"
            // Thêm các URI khác bạn muốn bỏ qua filter JWT vào đây
    );

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // ===============================================================
        // BƯỚC MỚI: KIỂM TRA ĐƯỜNG DẪN CÔNG KHAI VÀ BỎ QUA FILTER
        // ===============================================================
        String requestURI = request.getRequestURI();

        // Cần đảm bảo rằng chỉ kiểm tra phần cuối của URI nếu SecurityConfig dùng kiểu pattern "**/getAllUser"
        // Để đơn giản, ta kiểm tra xem URI có chứa path công khai không.
        boolean isPublicUri = PUBLIC_URIS.stream()
                .anyMatch(uri -> requestURI.endsWith(uri));

        if (isPublicUri) {
            // Nếu là đường dẫn công khai, bỏ qua các bước kiểm tra JWT
            // và chuyển thẳng đến filter tiếp theo (hoặc endpoint cuối cùng).
            filterChain.doFilter(request, response);
            return; // Rất quan trọng: phải thoát khỏi phương thức
        }
        // ===============================================================

        final String authorizationHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;
        String role = null;

        // 1. Kiểm tra Header và lấy Token
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7);

            // BẮT ĐẦU KHỐI TRY-CATCH
            try {
                // 2. Trích xuất thông tin (Việc này đồng thời xác minh chữ ký)
                username = jwtService.extractUsername(token);
                role = jwtService.extractRole(token);

            } catch (Exception e) {
                // Nếu có lỗi JWT, username và role vẫn là null.
            }
        }

        // 3. Tiến hành Xác thực nếu thông tin hợp lệ và chưa được xác thực
        if (username != null && role != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // 4. Kiểm tra Logic (Username, Role, Hạn sử dụng)
            if (jwtService.validateToken(token, userDetails.getUsername(), role)) {
                // Gán quyền từ role
                List<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()));

                // Xác thực người dùng với Spring Security
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        // 5. Cho request đi tiếp trong chuỗi Filter
        filterChain.doFilter(request, response);
    }
}