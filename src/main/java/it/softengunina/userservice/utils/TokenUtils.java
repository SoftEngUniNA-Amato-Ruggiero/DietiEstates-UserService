package it.softengunina.userservice.utils;

import it.softengunina.userservice.exceptions.AuthenticationNotFoundException;
import it.softengunina.userservice.exceptions.GroupClaimException;
import it.softengunina.userservice.exceptions.JwtNotFoundException;
import it.softengunina.userservice.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class TokenUtils {
    private static final Logger log = LoggerFactory.getLogger(TokenUtils.class);

    private TokenUtils() {
    }

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

    public static void logTokenData(Jwt jwt) {
        String cognitoSub = jwt.getSubject();
        String username = jwt.getClaimAsString("username");
        log.info("Authenticated user: {} ({})", cognitoSub, username);
        log.info("{}", jwt.getClaims());
    }

//    public static Role getRoleFromToken(Jwt jwt) throws GroupClaimException {
//        List<String> groups = jwt.getClaimAsStringList("cognito:groups");
//        return getRoleFromGroups(groups);
//    }
//
//    public static Role getRoleFromGroups(List<String> groups) throws GroupClaimException {
//        if (groups == null || groups.isEmpty()) {
//            log.error("groups: {}", groups);
//            throw new GroupClaimException("groups claim is empty");
//        }
//        if (groups.contains(Role.AGENCY_MANAGER.name())) {
//            return Role.AGENCY_MANAGER;
//        }
//        if (groups.contains(Role.REAL_ESTATE_AGENT.name())) {
//            return Role.REAL_ESTATE_AGENT;
//        }
//        if (groups.contains(Role.CUSTOMER.name())) {
//            return Role.CUSTOMER;
//        }
//        throw new GroupClaimException("Unknown user group: " + groups);
//    }

    public static String getCognitoSubFromToken(Jwt jwt) {
        String cognitoSub = jwt.getSubject();
        if (cognitoSub == null || cognitoSub.isEmpty()) {
            throw new JwtNotFoundException("Cognito sub claim is missing or empty.");
        }
        return cognitoSub;
    }

    // The following methods only work with the ID Token, not the Access Token.

    public static LoginCredentials getCredentialsFromToken(Jwt jwt) {
        return new LoginCredentials(
                jwt.getClaimAsString("email"),
                jwt.getClaimAsString("sub")
        );
    }

    public static PersonInfo getInfoFromToken(Jwt jwt) {
        return new PersonInfo(
                jwt.getClaimAsString("given_name"),
                jwt.getClaimAsString("family_name")
        );
    }
}
