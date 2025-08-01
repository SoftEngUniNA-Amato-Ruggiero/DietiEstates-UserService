package it.softengunina.userservice.model;
import jakarta.persistence.*;

@Entity
@Table(name = "real_estate_agents")
public class RealEstateAgent extends User{
    private static final Role ROLE = Role.REAL_ESTATE_AGENT;

    @ManyToOne
    @JoinColumn(name = "agency_id")
    protected RealEstateAgency agency;

    public RealEstateAgent(LoginCredentials credentials, PersonInfo info, RealEstateAgency agency) {
        super(credentials, info);
        this.agency = agency;
    }

    protected RealEstateAgent(){
        super();
    }

    @Override
    public Role getPermissions(){
        return ROLE;
    }

    public RealEstateAgency getAgency() {
        return agency;
    }

    @Override
    public String toString(){
        return super.toString() + " " + agency.toString();
    }

    protected void setAgency(RealEstateAgency agency) {
        this.agency = agency;
    }
}
