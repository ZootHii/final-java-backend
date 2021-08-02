package com.zoothii.finaljavabackend.business.abstracts;

import com.zoothii.finaljavabackend.core.utulities.results.DataResult;
import com.zoothii.finaljavabackend.core.utulities.results.Result;
import com.zoothii.finaljavabackend.entities.payload.request.LoginRequest;
import com.zoothii.finaljavabackend.entities.payload.request.RegisterRequest;
import com.zoothii.finaljavabackend.entities.payload.response.JwtResponse;

public interface AuthService {
    DataResult<JwtResponse> register(RegisterRequest registerRequest);
    DataResult<JwtResponse> login(LoginRequest loginRequest);

    // todo these methods will be moved to userService after creating
    //Result checkIfUserNameExists(String userName);
    //Result checkIfEmailExists(String email);
}
