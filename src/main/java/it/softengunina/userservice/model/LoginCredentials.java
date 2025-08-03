package it.softengunina.userservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@ToString
public class LoginCredentials {
    @NotBlank
    @Email
    @Column(unique = true, nullable = false)
    @Getter
    @Setter
    @NonNull
    private String email;

    @NotBlank
    @Column(unique = true, nullable = false)
    @Getter
    @Setter
    @NonNull
    private String cognitoSub;
}

