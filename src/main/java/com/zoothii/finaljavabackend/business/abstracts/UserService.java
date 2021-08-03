package com.zoothii.finaljavabackend.business.abstracts;

import com.zoothii.finaljavabackend.core.entities.User;
import com.zoothii.finaljavabackend.core.utulities.results.DataResult;

import java.util.List;

public interface UserService {
    DataResult<List<User>> getUsers();
}