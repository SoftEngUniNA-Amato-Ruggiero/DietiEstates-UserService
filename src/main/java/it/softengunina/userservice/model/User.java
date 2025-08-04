package it.softengunina.userservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    @Setter(AccessLevel.PROTECTED)
    private Long id;

    @Version
    @Getter
    @Setter(AccessLevel.PROTECTED)
    private Long version;

    @Embedded
    @NotNull
    @Getter
    @Setter
    @NonNull
    private LoginCredentials credentials;

    @Embedded
    @NotNull
    @Getter
    @Setter
    @NonNull
    private PersonInfo info;

    public User(@NonNull LoginCredentials credentials,
                @NonNull PersonInfo info) {
        this.credentials = credentials;
        this.info = info;
    }
}
