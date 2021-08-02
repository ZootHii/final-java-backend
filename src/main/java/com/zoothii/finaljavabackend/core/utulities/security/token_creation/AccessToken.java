package com.zoothii.finaljavabackend.core.utulities.security.token_creation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccessToken {
    private String token;
    private Date expiration;
}
