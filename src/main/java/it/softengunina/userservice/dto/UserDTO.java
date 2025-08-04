package it.softengunina.userservice.dto;

import it.softengunina.userservice.model.PersonInfo;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
public class UserDTO {
    @NotBlank
    private String email;

    @NotNull
    @Valid
    private PersonInfo info;
}
