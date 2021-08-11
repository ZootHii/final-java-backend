package com.zoothii.finaljavabackend.business.abstracts;

import com.zoothii.finaljavabackend.core.entities.Role;
import com.zoothii.finaljavabackend.core.utulities.constants.Roles;
import com.zoothii.finaljavabackend.core.utulities.results.DataResult;
import com.zoothii.finaljavabackend.core.utulities.results.Result;

import java.util.List;

public interface RoleService {

    DataResult<List<Role>> getRoles() throws InterruptedException;

    DataResult<Role> getRole(Role role);

    Result createRole(Role role);

    Result deleteRole(Role role);

    DataResult<Role> getRoleByName(String name);

    Result checkIfRoleExistsByName(String role);

    Result checkIfRoleExistsById(int id);


    //Result checkIfRoleIsNotExists(String role);

    Result createDefaultRoleIfNotExists(String defaultRole);
}
