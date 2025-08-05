package it.softengunina.userservice.utils;

import it.softengunina.userservice.exceptions.AuthenticationNotFoundException;
import it.softengunina.userservice.exceptions.JwtNotFoundException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TokenUtils {
    public static Authentication getAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new AuthenticationNotFoundException("No Authentication found in SecurityContext.");
        }
        return authentication;
    }

    public static Jwt getJwt(Authentication authentication) {
        if (authentication.getPrincipal() instanceof Jwt jwt) {
            return jwt;
        } else {
            throw new JwtNotFoundException("Authentication object is not an instance of Jwt.");
        }
    }

    public static Jwt getJwt() {
        Authentication authentication = getAuthentication();
        return getJwt(authentication);
    }

    public static String getCognitoSubFromToken(Jwt jwt) {
        String cognitoSub = jwt.getSubject();
        if (cognitoSub == null || cognitoSub.isEmpty()) {
            throw new JwtNotFoundException("Cognito sub claim is missing or empty.");
        }
        return cognitoSub;
    }

    public static void logTokenData(Jwt jwt) {
        String cognitoSub = jwt.getSubject();
        String username = jwt.getClaimAsString("username");
        log.info("Authenticated user: {} ({})", cognitoSub, username);
        log.info("{}", jwt.getClaims());
    }
}
