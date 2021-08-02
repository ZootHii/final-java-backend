package com.zoothii.finaljavabackend.business.concretes;

import com.zoothii.finaljavabackend.business.abstracts.AuthService;
import com.zoothii.finaljavabackend.core.data_access.RoleRepository;
import com.zoothii.finaljavabackend.core.data_access.UserRepository;
import com.zoothii.finaljavabackend.core.entities.ERole;
import com.zoothii.finaljavabackend.core.entities.Role;
import com.zoothii.finaljavabackend.core.entities.User;
import com.zoothii.finaljavabackend.core.utulities.results.*;
import com.zoothii.finaljavabackend.core.utulities.security.jwt.JwtUtils;
import com.zoothii.finaljavabackend.core.utulities.security.services.UserDetailsImpl;
import com.zoothii.finaljavabackend.entities.payload.request.LoginRequest;
import com.zoothii.finaljavabackend.entities.payload.request.RegisterRequest;
import com.zoothii.finaljavabackend.entities.payload.response.JwtResponse;
import com.zoothii.finaljavabackend.entities.payload.response.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public DataResult<JwtResponse> register(RegisterRequest registerRequest) {

        // TODO checkIf methods will be created // DONE
        /*if (userRepository.existsByUsername(registerRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }
        */

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
        Set<String> strRoles = registerRequest.getRole();
        Set<Role> roles = new HashSet<>();

        // TODO test user role without giving it when registering

        // set requested roles checking from database for security
        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);
                        break;
                    case "mod":
                        Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);
                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        // set requested roles and save
        user.setRoles(roles);
        userRepository.save(user);
        return new SuccessDataResult<>(generateJwtResponseUsernamePassword(registerRequest.getUsername(), registerRequest.getPassword()), "User registered successfully!");
    }

    @Override
    public DataResult<JwtResponse> login(LoginRequest loginRequest) {
        return new SuccessDataResult<>(generateJwtResponseUsernamePassword(loginRequest.getUsername(), loginRequest.getPassword()), "User registered successfully!");
    }

    // authenticate user and return jwt as response
    public JwtResponse generateJwtResponseUsernamePassword(String username, String password){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority()).collect(Collectors.toList());

        JwtResponse jwtResponse = new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles);
        return jwtResponse;
    }

    // *** BUSINESS RULES ***
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
