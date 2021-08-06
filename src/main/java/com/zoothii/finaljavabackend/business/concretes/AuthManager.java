package com.zoothii.finaljavabackend.business.concretes;

import com.zoothii.finaljavabackend.business.abstracts.AuthService;
import com.zoothii.finaljavabackend.business.abstracts.RoleService;
import com.zoothii.finaljavabackend.business.abstracts.UserService;
import com.zoothii.finaljavabackend.core.entities.Role;
import com.zoothii.finaljavabackend.core.entities.User;
import com.zoothii.finaljavabackend.core.utulities.results.*;
import com.zoothii.finaljavabackend.core.utulities.security.token.AccessToken;
import com.zoothii.finaljavabackend.core.utulities.security.token.jwt.JwtUtils;
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
    final private UserService userService;
    final private RoleService roleService;
    final private PasswordEncoder passwordEncoder;
    final private JwtUtils jwtUtils;

    @Autowired
    public AuthManager(AuthenticationManager authenticationManager, UserService userService, RoleService roleService, PasswordEncoder passwordEncoder, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public DataResult<UserResponse> register(RegisterRequest registerRequest) {

        Result resultUsernameExists = userService.checkIfUsernameExists(registerRequest.getUsername());
        if (resultUsernameExists.isSuccess()) {
            return new ErrorDataResult<>(resultUsernameExists.getMessage());
        }
        Result resultEmailExists = userService.checkIfEmailExists(registerRequest.getEmail());
        if (resultEmailExists.isSuccess()) {
            return new ErrorDataResult<>(resultEmailExists.getMessage());
        }

        // Create new user's account and hash password
        User user = new User(registerRequest.getUsername(), registerRequest.getEmail(), passwordEncoder.encode(registerRequest.getPassword()));
        Set<String> strRoles = registerRequest.getRoles();
        Set<Role> roles = new HashSet<>();

        // set requested roles checking from database for security
        if (strRoles == null) {
            // TODO default ROLE_USER ekle database de yoksa önce database e ekle işlemini yap
            Result resultRoleIsNotExists = roleService.checkIfRoleIsNotExists("ROLE_USER");

            if (resultRoleIsNotExists.isSuccess()) {
                return new ErrorDataResult<>(resultRoleIsNotExists.getMessage());
            }

            roles.add(roleService.getRoleByName("ROLE_USER").getData());

        } else {
            for (String role : strRoles) {
                Result resultRoleExists = roleService.checkIfRoleExists(role);
                if (resultRoleExists.isSuccess()) {
                    Role roleToAdd = roleService.getRoleByName(role).getData();
                    roles.add(roleToAdd);
                }
                Result resultRoleIsNotExists = roleService.checkIfRoleIsNotExists(role);
                if (resultRoleIsNotExists.isSuccess()){
                    return new ErrorDataResult<>(resultRoleIsNotExists.getMessage());
                }
            }
        }
        // set requested roles and save
        user.setRoles(roles);
        userService.createUser(user);
        return new SuccessDataResult<>(generateJwtResponseUsernamePassword(registerRequest.getUsername(), registerRequest.getPassword()), "User registered successfully!");
    }

    @Override
    public DataResult<UserResponse> login(LoginRequest loginRequest) {
        // check username exist from service
        Result resultUserNameIsNotExists = userService.checkIfUsernameIsNotExists(loginRequest.getUsername());
        if (resultUserNameIsNotExists.isSuccess()) {
            return new ErrorDataResult<>(resultUserNameIsNotExists.getMessage());
        }

        // check password true for the user
        DataResult<User> result = userService.getUserByUsername(loginRequest.getUsername());
        if (!passwordEncoder.matches(loginRequest.getPassword(), result.getData().getPassword())) {
            return new ErrorDataResult<>("Password is not correct.");
        }
        return new SuccessDataResult<>(generateJwtResponseUsernamePassword(loginRequest.getUsername(), loginRequest.getPassword()), "User logged in successfully!");
    }

    // authenticate user and return jwt as response
    private UserResponse generateJwtResponseUsernamePassword(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        AccessToken accessToken = jwtUtils.createAccessToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority()).collect(Collectors.toList());

        return new UserResponse(accessToken, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles);
    }
}
