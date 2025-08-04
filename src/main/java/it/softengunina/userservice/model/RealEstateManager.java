package it.softengunina.userservice.model;
import jakarta.persistence.*;
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

    public static RealEstateManager promoteUser(User user, RealEstateAgency agency) {
        RealEstateAgent agent = RealEstateAgent.promoteUser(user, agency);
        return promoteAgent(agent);
    }

    public static RealEstateManager promoteAgent(RealEstateAgent agent) {
        RealEstateManager manager = new RealEstateManager();
        manager.setId(agent.getId());
        manager.setVersion(agent.getVersion());
        manager.setCredentials(agent.getCredentials());
        manager.setInfo(agent.getInfo());
        manager.setAgency(agent.getAgency());
        return manager;
    }
}

