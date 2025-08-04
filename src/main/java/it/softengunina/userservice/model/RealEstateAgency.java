package it.softengunina.userservice.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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
    @Getter
    @Setter(AccessLevel.PROTECTED)
    private List<RealEstateManager> managers = new ArrayList<>();

    @OneToMany(mappedBy = "agency")
    @JsonManagedReference
    @Getter
    @Setter(AccessLevel.PROTECTED)
    private List<RealEstateAgent> agents = new ArrayList<>();

    public RealEstateAgency(@NonNull String iban,
                            @NonNull String name) {
        this.iban = iban;
        this.name = name;
    }
}

