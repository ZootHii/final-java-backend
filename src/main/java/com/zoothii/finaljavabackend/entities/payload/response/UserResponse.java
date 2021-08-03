package com.zoothii.finaljavabackend.entities.payload.response;

import com.zoothii.finaljavabackend.core.utulities.security.jwt.AccessToken;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class UserResponse {
	private AccessToken accessToken;
	//private String type = "Bearer";
	private Long id;
	private String username;
	private String email;
	private List<String> roles;

	public UserResponse(AccessToken accessToken, Long id, String username, String email, List<String> roles) {
		this.accessToken = accessToken;
		this.id = id;
		this.username = username;
		this.email = email;
		this.roles = roles;
	}
}
