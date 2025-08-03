package it.softengunina.userservice.model;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "real_estate_managers")
@PrimaryKeyJoinColumn(name = "id")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = true)
@ToString
public final class RealEstateManager extends RealEstateAgent {
    public RealEstateManager(@NonNull LoginCredentials loginCredentials,
                             @NonNull PersonInfo personInfo,
                             @NonNull RealEstateAgency agency) {
        super(loginCredentials, personInfo, agency);
    }

    public static RealEstateManager promoteUser(User user, RealEstateAgency agency) {
        RealEstateAgent agent = RealEstateAgent.promoteUser(user, agency);
        return promoteAgent(agent);
    }

    public static RealEstateManager promoteAgent(RealEstateAgent agent) {
        RealEstateManager manager = new RealEstateManager();
        manager.id = agent.getId();
        manager.version = agent.getVersion();
        manager.credentials = agent.getCredentials();
        manager.info = agent.getInfo();
        manager.agency = agent.getAgency();
        return manager;
    }
}

