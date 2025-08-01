package it.softengunina.userservice.model;

/**
 * Enum representing the different roles a user can have in the system.
 * Must match the names of the user groups defined in AWS Cognito.
 */
public enum Role {
    CUSTOMER,
    REAL_ESTATE_AGENT,
    AGENCY_MANAGER,
    SYSADMIN;
}
