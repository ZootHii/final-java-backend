package com.zoothii.finaljavabackend.business.abstracts;

import com.zoothii.finaljavabackend.core.entities.Role;
import com.zoothii.finaljavabackend.core.utulities.results.DataResult;
import com.zoothii.finaljavabackend.core.utulities.results.Result;

import java.util.List;

public interface RoleService {

    DataResult<List<Role>> getRoles();

    Result createRole(Role role);

}
