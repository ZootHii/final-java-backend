package com.zoothii.finaljavabackend.api.controllers;

import java.util.*;

import javax.validation.Valid;

import com.zoothii.finaljavabackend.business.abstracts.AuthService;
import com.zoothii.finaljavabackend.core.utulities.results.DataResult;
import com.zoothii.finaljavabackend.core.utulities.results.ErrorDataResult;
import com.zoothii.finaljavabackend.entities.payload.request.LoginRequest;
import com.zoothii.finaljavabackend.entities.payload.request.RegisterRequest;
import com.zoothii.finaljavabackend.entities.payload.response.JwtResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

//@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/")
public class AuthController {

	private final AuthService authService;

	@Autowired
	public AuthController(AuthService authService) {
		this.authService = authService;
	}


//	final private AuthenticationManager authenticationManager;
//
//	final private UserRepository userRepository;
//
//	final private RoleRepository roleRepository;
//
//	final private PasswordEncoder encoder;
//
//	final JwtUtils jwtUtils;
//
//	@Autowired
//	public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder encoder, JwtUtils jwtUtils) {
//		this.authenticationManager = authenticationManager;
//		this.userRepository = userRepository;
//		this.roleRepository = roleRepository;
//		this.encoder = encoder;
//		this.jwtUtils = jwtUtils;
//	}
//
//	@PostMapping("/signin")
//	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
//
//		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
//
//		SecurityContextHolder.getContext().setAuthentication(authentication);
//		String jwt = jwtUtils.generateJwtToken(authentication);
//
//		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
//		List<String> roles = userDetails.getAuthorities().stream()
//				.map(item -> item.getAuthority())
//				.collect(Collectors.toList());
//
//		return ResponseEntity.ok(new JwtResponse(jwt,
//												 userDetails.getId(),
//												 userDetails.getUsername(),
//												 userDetails.getEmail(),
//												 roles));
//	}
//
//	@PostMapping("/signup")
//	public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest signUpRequest) {
//		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
//			return ResponseEntity
//					.badRequest()
//					.body(new MessageResponse("Error: Username is already taken!"));
//		}
//
//		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
//			return ResponseEntity
//					.badRequest()
//					.body(new MessageResponse("Error: Email is already in use!"));
//		}
//
//		// Create new user's account
//		User user = new User(signUpRequest.getUsername(),
//							 signUpRequest.getEmail(),
//							 encoder.encode(signUpRequest.getPassword()));
//
//		Set<String> strRoles = signUpRequest.getRole();
//		Set<Role> roles = new HashSet<>();
//
//		if (strRoles == null) {
//			Role userRole = roleRepository.findByName(ERole.ROLE_USER).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//			roles.add(userRole);
//		} else {
//			strRoles.forEach(role -> {
//				switch (role) {
//				case "admin":
//					Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//					roles.add(adminRole);
//
//					break;
//				case "mod":
//					Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
//							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//					roles.add(modRole);
//
//					break;
//				default:
//					Role userRole = roleRepository.findByName(ERole.ROLE_USER)
//							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//					roles.add(userRole);
//				}
//			});
//		}
//
//		user.setRoles(roles);
//		userRepository.save(user);
//
//		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
//	}



	@PostMapping("/login")
	public DataResult<JwtResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
		return authService.login(loginRequest);
	}

	@PostMapping("/register")
	public DataResult<JwtResponse> register(@Valid @RequestBody RegisterRequest registerRequest) {
		return authService.register(registerRequest);
	}


	// ****** VALIDATION ******
	// todo tek seferlik kullanım haline getir
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorDataResult<Object> validationExceptionHandler(MethodArgumentNotValidException exception){
		Map<String, String> validationErrors = new HashMap<>();
		for (FieldError fieldError : exception.getBindingResult().getFieldErrors()){
			validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
		}
		return new ErrorDataResult<>(validationErrors, "validation errors");
	}

}
