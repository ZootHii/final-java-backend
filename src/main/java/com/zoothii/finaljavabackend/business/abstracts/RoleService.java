package com.zoothii.finaljavabackend.business.abstracts;

import com.zoothii.finaljavabackend.core.entities.Role;
import com.zoothii.finaljavabackend.core.utulities.constants.Roles;
import com.zoothii.finaljavabackend.core.utulities.results.DataResult;
import com.zoothii.finaljavabackend.core.utulities.results.Result;

import java.util.List;

public interface RoleService {

    DataResult<List<Role>> getRoles() throws InterruptedException;

    Result createRole(Role role);

    Result deleteRole(Role role);

    DataResult<Role> getRoleByName(String name);

    Result checkIfRoleExists(String role);

    //Result checkIfRoleIsNotExists(String role);

    Result createDefaultRoleIfNotExists(String defaultRole);
}
