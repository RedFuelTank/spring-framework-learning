package security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.orm.hibernate5.SpringSessionContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import security.TokenInfo;

import java.io.IOException;

public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private String key;

    public JwtAuthorizationFilter(String key) {
        this.key = key;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String token = request.getHeader("Authorization");

        if (token == null) {
            chain.doFilter(request, response);
            return;
        }

        TokenInfo info = new JwtUtil(key).decode(token);

        var authorities = info.getRoles().stream()
                .map(SimpleGrantedAuthority::new)
                .toList();

        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
                info.getName(),
                null,
                authorities
        ));

        chain.doFilter(request, response);
    }
}
