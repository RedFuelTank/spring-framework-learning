package security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import security.handlers.ApiAuthenticationFailure;
import security.handlers.ApiAuthenticationSuccess;

import java.io.IOException;
import java.util.stream.Collectors;

public class ApiAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    protected ApiAuthenticationFilter(AuthenticationManager authenticationManager, String url) {
        super(url);

        setAuthenticationManager(authenticationManager);
        setAuthenticationSuccessHandler(new ApiAuthenticationSuccess());
        setAuthenticationFailureHandler(new ApiAuthenticationFailure());
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        String body = request.getReader().lines().collect(Collectors.joining());

        LoginCredentials loginCredentials = new ObjectMapper().readValue(body, LoginCredentials.class);

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                loginCredentials.getUserName(),
                loginCredentials.getPassword()
        );

        return getAuthenticationManager().authenticate(token);
    }
}
