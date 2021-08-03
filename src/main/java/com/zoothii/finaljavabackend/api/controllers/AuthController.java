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
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

//@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/")
@CrossOrigin
public class AuthController {

	private final AuthService authService;

	@Autowired
	public AuthController(AuthService authService) {
		this.authService = authService;
	}

	@PostMapping("/login")
	public DataResult<JwtResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
		return authService.login(loginRequest);
	}

	@PostMapping("/register")
	public DataResult<JwtResponse> register(@Valid @RequestBody RegisterRequest registerRequest) {
		return authService.register(registerRequest);
	}


//	// ****** VALIDATION ******
//	// todo tek seferlik kullanım haline getir
//	@ExceptionHandler(MethodArgumentNotValidException.class)
//	@ResponseStatus(HttpStatus.BAD_REQUEST)
//	public ErrorDataResult<Object> validationExceptionHandler(MethodArgumentNotValidException exception){
//		Map<String, String> validationErrors = new HashMap<>();
//		for (FieldError fieldError : exception.getBindingResult().getFieldErrors()){
//			validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
//		}
//		return new ErrorDataResult<>(validationErrors, "validation errors");
//	}

}
