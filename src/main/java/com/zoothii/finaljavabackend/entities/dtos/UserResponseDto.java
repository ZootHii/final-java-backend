package com.zoothii.finaljavabackend.entities.dtos;

import com.zoothii.finaljavabackend.core.utulities.security.token_creation.AccessToken;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {
    public int id;
    public String firstName;
    public String lastName;
    public String email;
    public AccessToken accessToken;
}
