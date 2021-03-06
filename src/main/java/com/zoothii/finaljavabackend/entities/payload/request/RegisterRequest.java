package com.zoothii.finaljavabackend.entities.payload.request;

import com.zoothii.finaljavabackend.core.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

import javax.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    @NotBlank
    @Size(min = 3, max = 20)
    private String username;
 
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    private Set<String> roles;

    @NotBlank
    @Size(min = 6, max = 40)
    // TODO activate later @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,20}$", message = "one special character among @#$%, one number, one lowercase character, one uppercase character, less than 20 and more than 8 characters")
    private String password;
}
