package it.softengunina.userservice.filter;

import it.softengunina.userservice.exceptions.AuthenticationNotFoundException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static it.softengunina.userservice.utils.TokenUtils.*;

@Component
@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(
            @NonNull final HttpServletRequest request,
            @NonNull final HttpServletResponse response,
            @NonNull final FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            Jwt jwt = getJwt();
            logTokenData(jwt);
        } catch (AuthenticationNotFoundException e) {
            log.warn(e.getMessage());
        } catch (Exception e) {
            log.error("An unexpected error occurred while processing the JWT.", e);
        }
        filterChain.doFilter(request, response);
    }
}
