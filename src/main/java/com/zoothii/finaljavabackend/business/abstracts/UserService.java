package com.zoothii.finaljavabackend.business.abstracts;

import com.zoothii.finaljavabackend.core.entities.Role;
import com.zoothii.finaljavabackend.core.entities.User;
import com.zoothii.finaljavabackend.core.utulities.results.DataResult;
import com.zoothii.finaljavabackend.core.utulities.results.Result;

import java.util.List;
import java.util.Set;

public interface UserService {
    Result createUser(User user);

    DataResult<List<User>> getUsers();

    DataResult<User> getUserByUsername(String username);

    DataResult<User> getUserByEmail(String email);

    Result setRolesToUser(String username, Set<Role> roles);

    Result deleteUser(User user);

    Result setNewRolesToUser(String username, Set<Role> roles);

    Result checkIfUsernameExists(String username);

    //Result checkIfUsernameIsNotExists(String username);

    Result checkIfEmailExists(String email);

    //Result checkIfEmailIsNotExists(String email);
}