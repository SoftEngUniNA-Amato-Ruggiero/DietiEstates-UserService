package it.softengunina.userservice.utils;

import it.softengunina.userservice.exceptions.GroupClaimException;
import it.softengunina.userservice.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class TokenUtils {
    private static final Logger log = LoggerFactory.getLogger(TokenUtils.class);

    private static final Map<Role, String> roleAuthorityMap = new EnumMap<>(Role.class);
    static {
        for (Role role : Role.values()) {
            roleAuthorityMap.put(role, "ROLE_" + role.name().toUpperCase());
        }
    }

    private TokenUtils() {
    }

    public static void logTokenData(Jwt jwt) {
        String cognitoSub = jwt.getSubject();
        String username = jwt.getClaimAsString("username");
        log.info("Authenticated user: {} ({})", cognitoSub, username);
        log.info("{}", jwt.getClaims());
    }

    // TODO: Refactor this to use a factory pattern
    public static User getUserFromToken(Jwt jwt) throws GroupClaimException {
        Role role = getRoleFromToken(jwt);
        LoginCredentials credentials = getCredentialsFromToken(jwt);
        PersonInfo info = getInfoFromToken(jwt);

        if (role == Role.CUSTOMER) {
            return new Customer(credentials, info);
        }

        RealEstateAgency agency = getAgencyFromToken(jwt);

        if (role == Role.REAL_ESTATE_AGENT) {
            return new RealEstateAgent(credentials, info, agency);
        }
        if (role == Role.AGENCY_MANAGER) {
            return new RealEstateManager(credentials, info, agency);
        }

        throw new RuntimeException("getRoleFromToken did not throw an exception");
    }

    public static Role getRoleFromToken(Jwt jwt) throws GroupClaimException {
        List<String> groups = jwt.getClaimAsStringList("cognito:groups");
        return getRoleFromGroups(groups);
    }

    public static Role getRoleFromGroups(List<String> groups) throws GroupClaimException {
        if (groups == null || groups.isEmpty()) {
            log.error("groups: {}", groups);
            throw new GroupClaimException("groups claim is empty");
        }
        if (groups.contains(Role.AGENCY_MANAGER.name())) {
            return Role.AGENCY_MANAGER;
        }
        if (groups.contains(Role.REAL_ESTATE_AGENT.name())) {
            return Role.REAL_ESTATE_AGENT;
        }
        if (groups.contains(Role.CUSTOMER.name())) {
            return Role.CUSTOMER;
        }
        throw new GroupClaimException("Unknown user group: " + groups);
    }

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

    //TODO: Implement this method to extract the agency information from the JWT token.
    public static RealEstateAgency getAgencyFromToken(Jwt jwt) {
        return new RealEstateAgency("iban", "name");
    }
}
