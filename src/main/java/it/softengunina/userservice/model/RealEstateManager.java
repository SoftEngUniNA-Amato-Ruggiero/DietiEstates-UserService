package it.softengunina.userservice.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "real_estate_managers")
@PrimaryKeyJoinColumn(name = "id")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = true)
@ToString
public class RealEstateManager extends RealEstateAgent {
    public RealEstateManager(@NonNull LoginCredentials loginCredentials,
                             @NonNull PersonInfo personInfo,
                             @NonNull RealEstateAgency agency) {
        super(loginCredentials, personInfo, agency);
    }

//    public RealEstateManager(@NonNull User user,
//                             @NonNull RealEstateAgency agency) {
//        super(user.getCredentials(), user.getInfo(), agency);
//        this.setId(user.getId());
//        this.setVersion(user.getVersion());
//        this.agency.addManager(this);
//    }

    @Override
    public Role getRole() {
        return Role.AGENCY_MANAGER;
    }

    @Override
    public void quitAgency() {
        this.agency.removeManager(this);
        super.quitAgency();
    }

    public RealEstateAgent addAgent(@NotNull User user) {
        RealEstateAgent agent = new RealEstateAgent(
                user.getCredentials(),
                user.getInfo(),
                this.agency
        );
        this.agency.addAgent(agent);
        return agent;
    }

    public RealEstateManager addManager(@NotNull RealEstateAgent agent) {
        if (agent.getAgency().equals(this.agency)) {
            RealEstateManager manager = new RealEstateManager(
                    agent.getCredentials(),
                    agent.getInfo(),
                    this.agency
            );
            manager.setId(agent.getId());
            this.agency.addManager(manager);
            return manager;
        } else {
            throw new IllegalArgumentException("The agent does not belong to your agency.");
        }
    }

    public RealEstateManager addManager(@NotNull User user) {
        RealEstateAgent agent = addAgent(user);
        return addManager(agent);
    }

    public void removeAgent(@NotNull RealEstateAgent agent) {
        if (agent.getAgency().equals(this.agency)) {
            agent.quitAgency();
        } else {
            throw new IllegalArgumentException("The agent does not belong to your agency.");
        }
    }
}

