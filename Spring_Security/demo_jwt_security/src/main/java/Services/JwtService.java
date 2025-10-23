package Services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtService {
    private static final String privateKey = "Kdsaflkj2kjf23uf9ds8dsakgnsa@Gsakljg";

    @Autowired
    private UserServiceImplement userServiceImplement;

    private Key getSignKey(){return Keys.hmacShaKeyFor(privateKey.getBytes());}

    public String generateToken(String userName){
        Map<String, String> claims = new HashMap<>();
        String roleName = userServiceImplement.findUserByName(userName).getRole();
        claims.put("role", roleName);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 5))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token, String userDetails, String role) {
        final String username = extractUsername(token);
        final String extractedRole = extractRole(token);
        return (username.equals(userDetails) && extractedRole.equals(role) && !isTokenExpired(token));
    }

    public Date extractExpiration(String token) {
        // BƯỚC 1: Xây dựng Bộ giải mã (Parser) với Khóa Bí mật
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()

                // BƯỚC 2: Giải mã và Xác minh Chữ ký (Bảo mật)
                .parseClaimsJws(token)

                // BƯỚC 3: Lấy ra Payload (Dữ liệu)
                .getBody();

        // BƯỚC 4: Lấy giá trị của trường 'expiration'
        return claims.getExpiration();
    }


    public boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }


    public String extractUsername(String token) {
        // BƯỚC 1: Xây dựng Bộ giải mã (Parser) với Khóa Bí mật
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()

                // BƯỚC 2: Giải mã và Xác minh Chữ ký (Bảo mật)
                .parseClaimsJws(token)

                // BƯỚC 3: Lấy ra Payload (Dữ liệu)
                .getBody();

        // BƯỚC 4: Lấy giá trị của trường 'subject'
        return claims.getSubject();
    }

    public String extractRole(String token) {
        // BƯỚC 1: Xây dựng Bộ giải mã (Parser) với Khóa Bí mật
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()

                // BƯỚC 2: Giải mã và Xác minh Chữ ký (Bảo mật)
                .parseClaimsJws(token)

                // BƯỚC 3: Lấy ra Payload (Dữ liệu)
                .getBody();

        // BƯỚC 4: Lấy giá trị của trường tùy chỉnh 'role'
        return claims.get("role", String.class);
    }
}
