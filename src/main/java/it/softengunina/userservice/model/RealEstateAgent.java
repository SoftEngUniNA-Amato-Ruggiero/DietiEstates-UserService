package it.softengunina.userservice.model;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "real_estate_agents")
@PrimaryKeyJoinColumn(name = "id")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = true)
@ToString
public class RealEstateAgent extends User {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agency_id")
    @JsonBackReference
    @NotNull
    @Getter
    @Setter
    @NonNull
    protected RealEstateAgency agency;

    public RealEstateAgent(@NonNull LoginCredentials credentials,
                           @NonNull PersonInfo info,
                           @NonNull RealEstateAgency agency) {
        super(credentials, info);
        this.agency = agency;
    }

    public static RealEstateAgent promoteUser(User user, RealEstateAgency agency) {
        RealEstateAgent agent = new RealEstateAgent();
        agent.setId(user.getId());
        agent.setVersion(user.getVersion());
        agent.setCredentials(user.getCredentials());
        agent.setInfo(user.getInfo());
        agent.setAgency(agency);
        return agent;
    }

    @Override
    public Role getRole() {
        return Role.REAL_ESTATE_AGENT;
    }
}
