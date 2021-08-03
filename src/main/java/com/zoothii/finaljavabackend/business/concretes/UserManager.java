package com.zoothii.finaljavabackend.business.concretes;


import com.zoothii.finaljavabackend.business.abstracts.UserService;
import com.zoothii.finaljavabackend.core.data_access.UserRepository;
import com.zoothii.finaljavabackend.core.entities.User;
import com.zoothii.finaljavabackend.core.utulities.results.DataResult;
import com.zoothii.finaljavabackend.core.utulities.results.SuccessDataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserManager implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserManager(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public DataResult<List<User>> getUsers() {
        return new SuccessDataResult<>(this.userRepository.findAll());
    }
}
