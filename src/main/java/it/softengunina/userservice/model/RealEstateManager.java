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

    @Override
    public Role getRole() {
        return Role.AGENCY_MANAGER;
    }
}

