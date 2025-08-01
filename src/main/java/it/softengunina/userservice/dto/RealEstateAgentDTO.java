package it.softengunina.userservice.dto;

import it.softengunina.userservice.model.LoginCredentials;
import it.softengunina.userservice.model.PersonInfo;

public class RealEstateAgentDTO {
    private LoginCredentials credentials;
    private PersonInfo info;
    private Long agencyId;

    public LoginCredentials getCredentials() {
        return credentials;
    }

    public PersonInfo getInfo() {
        return info;
    }

    public Long getAgencyId() {
        return agencyId;
    }

    public void setCredentials(LoginCredentials credentials) {
        this.credentials = credentials;
    }

    public void setInfo(PersonInfo info) {
        this.info = info;
    }

    public void setAgencyId(Long agencyId) {
        this.agencyId = agencyId;
    }
}
