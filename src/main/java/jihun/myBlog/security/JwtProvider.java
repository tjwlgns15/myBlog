package jihun.myBlog.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jihun.myBlog.exception.CustomException;
import jihun.myBlog.exception.ErrorCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtProvider {

    @Value("${jwt.key}")  // .yml
    private String key;

    private SecretKey secretKey;

    private JwtParser parser;

    @PostConstruct
    private void init() {
        secretKey = Keys.hmacShaKeyFor(key.getBytes());
        parser = Jwts.parser().verifyWith(secretKey).build();
    }

    public String generateToken(Long memberId) {
        return Jwts.builder()
                .subject(String.valueOf(memberId))
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 6))
                .signWith(secretKey, Jwts.SIG.HS256)
                .compact();
    }

    public String parserToken(String token) {
        try {
            Claims claim = parser.parseSignedClaims(token).getPayload();
            return claim.getSubject();
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }
    }

    public Claims extractClaims(String token) {
        return parser.parseSignedClaims(token).getPayload();
    }

    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    public boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    public boolean validateToken(String token, String username) {
        return (extractUsername(token).equals(username) && !isTokenExpired(token));
    }
}
