package com.zoothii.finaljavabackend.business.concretes;


import com.zoothii.finaljavabackend.business.abstracts.UserService;
import com.zoothii.finaljavabackend.core.data_access.UserDao;
import com.zoothii.finaljavabackend.core.entities.User;
import com.zoothii.finaljavabackend.core.utulities.results.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserManager implements UserService {

    private final UserDao userDao;

    @Autowired
    public UserManager(UserDao userDao) {
        this.userDao = userDao;
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
        Result resultUsernameIsNotExists = checkIfUsernameIsNotExists(username);
        if (resultUsernameIsNotExists.isSuccess()){
            return new ErrorDataResult<>(resultUsernameIsNotExists.getMessage());
        }

        User user = this.userDao.getUserByUsername(username);
        return new SuccessDataResult<>(user);
    }

    @Override
    public DataResult<User> getUserByEmail(String email) {
        Result resultEmailIsNotExists = checkIfEmailIsNotExists(email);
        if (resultEmailIsNotExists.isSuccess()){
            return new ErrorDataResult<>(resultEmailIsNotExists.getMessage());
        }

        User user = this.userDao.getUserByEmail(email);
        return new SuccessDataResult<>(user);
    }

    // *** BUSINESS RULES ***
    @Override
    public Result checkIfUsernameExists(String username) {
        User user = this.userDao.getUserByUsername(username);
        if (user == null) {
            return new ErrorResult();
        }
        return new SuccessResult("Username is exists.");
    }

    @Override
    public Result checkIfUsernameIsNotExists(String username) {
        User user = this.userDao.getUserByUsername(username);
        if (user != null) {
            return new ErrorResult();
        }
        return new SuccessResult("Username is not exists.");
    }

    @Override
    public Result checkIfEmailExists(String email) {
        User user = this.userDao.getUserByEmail(email);
        if (user == null) {
            return new ErrorResult();
        }
        return new SuccessResult("Email is exists.");
    }

    @Override
    public Result checkIfEmailIsNotExists(String email) {
        User user = this.userDao.getUserByEmail(email);
        if (user != null) {
            return new ErrorResult();
        }
        return new SuccessResult("Email is not exists.");
    }
}
