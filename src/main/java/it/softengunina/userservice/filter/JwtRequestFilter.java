package it.softengunina.userservice.filter;

import it.softengunina.userservice.exceptions.AuthenticationNotFoundException;
import it.softengunina.userservice.exceptions.JwtNotFoundException;
import it.softengunina.userservice.model.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static it.softengunina.userservice.utils.TokenUtils.getUserFromToken;
import static it.softengunina.userservice.utils.TokenUtils.logTokenData;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(JwtRequestFilter.class);

    @Override
    protected void doFilterInternal(
            @NonNull final HttpServletRequest request,
            @NonNull final HttpServletResponse response,
            @NonNull final FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            Authentication authentication = getAuthentication();
            Jwt jwt = getJwt(authentication);
            logTokenData(jwt);

            //TODO: Remove this, it's for testing purposes
            User user = getUserFromToken(jwt);
            log.info("{}", user);

        } catch (AuthenticationNotFoundException e) {
            log.warn(e.getMessage());
        } catch (Exception e) {
            log.error("An unexpected error occurred while processing the JWT.", e);
        }
        filterChain.doFilter(request, response);
    }

    private Authentication getAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new AuthenticationNotFoundException("No Authentication found in SecurityContext.");
        }
        return authentication;
    }

    private Jwt getJwt(Authentication authentication) {
        if (authentication.getPrincipal() instanceof Jwt jwt) {
            return jwt;
        } else {
            throw new JwtNotFoundException("Authentication object is not an instance of Jwt.");
        }
    }

}
