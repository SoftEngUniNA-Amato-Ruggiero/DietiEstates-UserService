package it.softengunina.userservice.dto;

import it.softengunina.userservice.model.PersonInfo;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
public class UserDTO {
    @NotBlank
    @Email
    private String email;

    @NotNull
    @Valid
    private PersonInfo info;
}
