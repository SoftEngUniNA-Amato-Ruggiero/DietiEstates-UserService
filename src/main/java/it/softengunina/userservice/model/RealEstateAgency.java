package it.softengunina.userservice.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "real_estate_agencies")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@ToString
public class RealEstateAgency{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter(AccessLevel.PROTECTED)
    private Long id;

    @Version
    @Getter
    @Setter(AccessLevel.PROTECTED)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Long version;

    @NotBlank
    @Column(unique = true, nullable = false)
    @Getter
    @Setter
    @NonNull
    private String iban;

    @NotBlank
    @Getter
    @Setter
    @NonNull
    private String name;

    @OneToMany(mappedBy = "agency")
    @JsonManagedReference
    @Setter(AccessLevel.PROTECTED)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<RealEstateManager> managers = new HashSet<>();

    @OneToMany(mappedBy = "agency")
    @JsonManagedReference
    @Setter(AccessLevel.PROTECTED)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<RealEstateAgent> agents = new HashSet<>();

    public RealEstateAgency(@NonNull String iban,
                            @NonNull String name) {
        this.iban = iban;
        this.name = name;
    }

    public Set<RealEstateManager> getManagers() {
        return Collections.unmodifiableSet(managers);
    }

    public Set<RealEstateAgent> getAgents() {
        return Collections.unmodifiableSet(agents);
    }

    public void addAgent(@NonNull RealEstateAgent agent) {
        agents.add(agent);
        agent.setAgency(this);
    }

    public void removeAgent(@NonNull RealEstateAgent agent) {
        managers.remove(agent);
        agents.remove(agent);
        agent.setAgency(null);
    }

    public void addManager(@NonNull RealEstateManager manager) {
        addAgent(manager);
        managers.add(manager);
    }

    public void removeManager(@NonNull RealEstateManager manager) {
        managers.remove(manager);
    }
}

