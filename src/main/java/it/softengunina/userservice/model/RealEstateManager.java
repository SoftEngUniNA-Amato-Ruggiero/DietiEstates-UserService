package it.softengunina.userservice.model;
import jakarta.persistence.*;

@Entity
@Table(name = "real_estate_managers")
public final class RealEstateManager extends RealEstateAgent {
    private static final Role ROLE = Role.AGENCY_MANAGER;

    public RealEstateManager(LoginCredentials credentials, PersonInfo info, RealEstateAgency agency) {
        super(credentials, info, agency);
    }

    protected RealEstateManager(){
        super();
    }

    @Override
    public Role getPermissions(){
        return ROLE;
    }
}

