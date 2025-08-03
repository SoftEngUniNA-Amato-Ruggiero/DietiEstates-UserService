package it.softengunina.userservice.model;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@EqualsAndHashCode
@ToString
public class PersonInfo {
    @NotBlank
    @Getter
    @Setter
    @NonNull
    private String firstName;

    @NotBlank
    @Getter
    @Setter
    @NonNull
    private String lastName;
}

