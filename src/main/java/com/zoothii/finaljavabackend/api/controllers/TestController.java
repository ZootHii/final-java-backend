package com.zoothii.finaljavabackend.api.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@CrossOrigin(origins = "*", maxAge = 3600) // investigate https://spring.io/guides/gs/rest-service-cors/#:~:text=This%20%40CrossOrigin%20annotation%20enables%20cross,of%2030%20minutes%20is%20used.
@RestController
@RequestMapping("/api/test")
public class TestController {
	@GetMapping("/all")
	public String allAccess() {
		return "Public Content.";
	}
	
//	@GetMapping("/user")
//	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
//	public String userAccess() {
//		return "User Content.";
//	}

	@GetMapping("/user")
	@PreAuthorize("hasAnyRole('USER','MODERATOR','ADMIN')")
	public String userAccess() {
		return "User Content.";
	}

	@GetMapping("/mod")
	@PreAuthorize("hasRole('MODERATOR')")
	public String moderatorAccess() {
		return "Moderator Board.";
	}

	@GetMapping("/admin")
	@PreAuthorize("hasRole('ADMIN')")
	public String adminAccess() {
		return "Admin Board.";
	}

	@GetMapping("/ben")
	@PreAuthorize("hasRole('BEN')")
	public String benAccess() {
		return "Ben Board.";
	}
}
