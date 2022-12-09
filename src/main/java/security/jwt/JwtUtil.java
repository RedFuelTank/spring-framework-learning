package security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import security.TokenInfo;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class JwtUtil {

    private String key;

    public JwtUtil(String key) {
        this.key = key;
    }


    public String encode(TokenInfo token) {
        return encode(token, LocalDateTime.now().plusMinutes(15));
    }

    public String encode(TokenInfo token, LocalDateTime time) {
        return Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(key.getBytes()), SignatureAlgorithm.HS512)
                .setSubject(token.getName())
                .setExpiration(asDate(time))
                .claim("roles", token.getRolesAsString())
                .compact();
    }

    private Date asDate(LocalDateTime time) {
        return Date.from(time.atZone(ZoneId.systemDefault()).toInstant());
    }

    public TokenInfo decode(String token) {
        String cleanToken = token.replace("Bearer ", "");

        Claims body = Jwts.parserBuilder()
                .setSigningKey(key.getBytes())
                .build()
                .parseClaimsJws(cleanToken)
                .getBody();

        return new TokenInfo(
                body.getSubject(),
                body.get("roles", String.class));
    }

}
