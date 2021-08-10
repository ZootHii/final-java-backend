package com.zoothii.finaljavabackend.business.concretes;


import com.zoothii.finaljavabackend.business.abstracts.RoleService;
import com.zoothii.finaljavabackend.business.abstracts.UserService;
import com.zoothii.finaljavabackend.core.data_access.UserDao;
import com.zoothii.finaljavabackend.core.entities.Role;
import com.zoothii.finaljavabackend.core.entities.User;
import com.zoothii.finaljavabackend.core.utulities.constants.Roles;
import com.zoothii.finaljavabackend.core.utulities.results.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserManager implements UserService {

    private final UserDao userDao;
    private final RoleService roleService;

    @Autowired
    public UserManager(UserDao userDao, RoleService roleService) {
        this.userDao = userDao;
        this.roleService = roleService;
    }

    @Override
    public Result createUser(User user) {
        userDao.save(user);
        return new SuccessResult("User created successfully.");
    }

    @Override
    public DataResult<List<User>> getUsers() {
        return new SuccessDataResult<>(this.userDao.findAll());
    }

    @Override
    public DataResult<User> getUserByUsername(String username) {
        Result resultUsernameExists = checkIfUsernameExists(username);
        if (!resultUsernameExists.isSuccess()) {
            return new ErrorDataResult<>(resultUsernameExists.getMessage());
        }

        User user = this.userDao.getUserByUsername(username);
        return new SuccessDataResult<>(user);
    }

    @Override
    public DataResult<User> getUserByEmail(String email) {
        Result resultEmailExists = checkIfEmailExists(email);
        if (!resultEmailExists.isSuccess()) {
            return new ErrorDataResult<>(resultEmailExists.getMessage());
        }

        User user = this.userDao.getUserByEmail(email);
        return new SuccessDataResult<>(user);
    }

    @Override
    public Result setRolesToUser(String username, Set<Role> roles) {
        var user = this.userDao.getUserByUsername(username);

        Set<String> strRoles = new HashSet<>();

        for (Role role: roles) {
            var resultCheckIfRoleExists = roleService.checkIfRoleExists(role.getName());
            if (resultCheckIfRoleExists.isSuccess()){
                strRoles.add(role.getName());
            } else {
                return new ErrorResult(resultCheckIfRoleExists.getMessage());
            }
        }

        if (!strRoles.contains(Roles.ROLE_USER)){
            var resultDefaultRole = roleService.getRoleByName(Roles.ROLE_USER);
            roles.add(resultDefaultRole.getData());
        }

        user.setRoles(roles);
        userDao.save(user);
        return new SuccessResult("roles set to user");
    }

    @Override
    public Result setNewRolesToUser(String username, Set<Role> roles) {

        for (Role role: roles) {
            var resultCheckIfRoleExists = roleService.checkIfRoleExists(role.getName());
            if (!resultCheckIfRoleExists.isSuccess()){
                return new ErrorResult(resultCheckIfRoleExists.getMessage());
            }
        }

        var user = this.userDao.getUserByUsername(username);
        var userRoles = user.getRoles();
        userRoles.addAll(roles);
        user.setRoles(userRoles);
        userDao.save(user);
        return new SuccessResult("new roles set to user");
    }

    // *** BUSINESS RULES ***
    @Override
    public Result checkIfUsernameExists(String username) {
        var user = this.userDao.getUserByUsername(username);
        if (user == null) {
            return new ErrorResult("Username is not exists.");
        }
        return new SuccessResult("Username is exists.");
    }

    @Override
    public Result checkIfEmailExists(String email) {
        User user = this.userDao.getUserByEmail(email);
        if (user == null) {
            return new ErrorResult("Email is not exists.");
        }
        return new SuccessResult("Email is exists.");
    }
}
