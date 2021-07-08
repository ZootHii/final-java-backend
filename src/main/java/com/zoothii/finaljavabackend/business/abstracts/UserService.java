package com.zoothii.finaljavabackend.business.abstracts;

import com.zoothii.finaljavabackend.core.entities.User;
import com.zoothii.finaljavabackend.core.utulities.results.DataResult;

public interface UserService {
    DataResult<User> createUser(User user);

    DataResult<User> getByEmail(String email);

}
