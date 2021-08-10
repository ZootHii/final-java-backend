package com.zoothii.finaljavabackend.business.abstracts;

import com.zoothii.finaljavabackend.core.utulities.results.DataResult;
import com.zoothii.finaljavabackend.core.utulities.results.Result;
import com.zoothii.finaljavabackend.entities.payload.request.LoginRequest;
import com.zoothii.finaljavabackend.entities.payload.request.RegisterRequest;
import com.zoothii.finaljavabackend.entities.payload.response.UserResponse;

public interface AuthService {
    DataResult<UserResponse> register(RegisterRequest registerRequest);

    DataResult<UserResponse> login(LoginRequest loginRequest);

    Result checkIfPasswordCorrect(String requestedPassword, String encryptedPassword);
}
