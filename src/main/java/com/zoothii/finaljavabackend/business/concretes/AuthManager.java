package com.zoothii.finaljavabackend.business.concretes;

import com.zoothii.finaljavabackend.business.abstracts.AuthService;
import com.zoothii.finaljavabackend.core.data_access.RoleRepository;
import com.zoothii.finaljavabackend.core.data_access.UserRepository;
import com.zoothii.finaljavabackend.core.entities.Role;
import com.zoothii.finaljavabackend.core.entities.User;
import com.zoothii.finaljavabackend.core.utulities.results.*;
import com.zoothii.finaljavabackend.core.utulities.security.jwt.AccessToken;
import com.zoothii.finaljavabackend.core.utulities.security.jwt.JwtUtils;
import com.zoothii.finaljavabackend.core.utulities.security.services.UserDetailsImpl;
import com.zoothii.finaljavabackend.entities.payload.request.LoginRequest;
import com.zoothii.finaljavabackend.entities.payload.request.RegisterRequest;
import com.zoothii.finaljavabackend.entities.payload.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthManager implements AuthService {

    final private AuthenticationManager authenticationManager;
    final private UserRepository userRepository;
    final private RoleRepository roleRepository;
    final private PasswordEncoder encoder;
    final JwtUtils jwtUtils;

    @Autowired
    public AuthManager(AuthenticationManager authenticationManager, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder encoder, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
    }


    @Override
    public DataResult<UserResponse> register(RegisterRequest registerRequest) {

        Result resultUserNameExists = checkIfUserNameExists(registerRequest.getUsername());
        Result resultEmailExists = checkIfEmailExists(registerRequest.getEmail());

        if (!resultUserNameExists.isSuccess()){
            return new ErrorDataResult<>(resultUserNameExists.getMessage());
        }
        if (!resultEmailExists.isSuccess()){
            return new ErrorDataResult<>(resultEmailExists.getMessage());
        }

        // Create new user's account and hash password
        User user = new User(registerRequest.getUsername(), registerRequest.getEmail(), encoder.encode(registerRequest.getPassword()));
        Set<String> strRoles = registerRequest.getRoles();
        Set<Role> roles = new HashSet<>();

        // TODO test user role without giving it when registering

        // set requested roles checking from database for security
        if (strRoles == null){
            // TODO default ROLE_USER ekle database de yoksa önce database e ekle işlemini yap
            Role userRole = roleRepository.getByName("ROLE_USER");
            if (userRole == null){
                return new ErrorDataResult<>("default role user yok");
            }
            roles.add(userRole);
        } else {
            for (String role : strRoles) {
                if (checkIfRoleExists(role).isSuccess()){
                    Role roleToAdd = roleRepository.getByName(role);
                    roles.add(roleToAdd);
                }
            }
        }
        // set requested roles and save
        user.setRoles(roles);
        userRepository.save(user);
        return new SuccessDataResult<>(generateJwtResponseUsernamePassword(registerRequest.getUsername(), registerRequest.getPassword()), "User registered successfully!");
    }

    @Override
    public DataResult<UserResponse> login(LoginRequest loginRequest) {
        return new SuccessDataResult<>(generateJwtResponseUsernamePassword(loginRequest.getUsername(), loginRequest.getPassword()), "User logged in successfully!");
    }

    // authenticate user and return jwt as response
    public UserResponse generateJwtResponseUsernamePassword(String username, String password){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        AccessToken accessToken = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority()).collect(Collectors.toList());

        return new UserResponse(accessToken, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles);
    }

    // *** BUSINESS RULES ***
    public Result checkIfRoleExists(String role){
        if (roleRepository.getByName(role) == null){
            return new ErrorResult("Error: Role "+role+" is not found.");
        }
        return new SuccessResult();
    }

    public Result checkIfUserNameExists(String userName) {
        if (userRepository.existsByUsername(userName)){
            return new ErrorResult("Error: Username is already taken!");
        }
        return new SuccessResult();
    }

    public Result checkIfEmailExists(String email) {
        if (userRepository.existsByEmail(email)){
            return new ErrorResult("Email is already in use!");
        }
        return new SuccessResult();
    }


}
