package security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import security.ApiAuthenticationFilter;
import security.TokenInfo;

import java.io.IOException;

public class JwtAuthenticationFilter extends ApiAuthenticationFilter {
    private String key;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, String url, String key) {
        super(authenticationManager, url);

        this.key = key;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        User principal = (User) authResult.getPrincipal();

        TokenInfo tokenInfo = new TokenInfo(
                principal.getUsername(),
                principal.getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .toList()
        );

        String encodedToken = new JwtUtil(key).encode(tokenInfo);

        response.setHeader("Authorization", "Bearer " + encodedToken);
    }
}
