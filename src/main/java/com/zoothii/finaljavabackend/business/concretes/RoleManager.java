package com.zoothii.finaljavabackend.business.concretes;

import com.zoothii.finaljavabackend.business.abstracts.RoleService;
import com.zoothii.finaljavabackend.core.data_access.RoleRepository;
import com.zoothii.finaljavabackend.core.entities.Role;
import com.zoothii.finaljavabackend.core.utulities.results.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleManager implements RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleManager(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public DataResult<List<Role>> getRoles() {
        return new SuccessDataResult<>(roleRepository.findAll(), "Roles are successfully listed.");
    }

    @Override
    public Result createRole(Role role) {
        Result resultRoleExists = checkIfRoleExists(role.getName());
        if (!resultRoleExists.isSuccess()){
            return new ErrorResult(resultRoleExists.getMessage());
        }

        roleRepository.save(role);
        return new SuccessResult("Role "+role.getName()+" is successfully created.");
    }

    // *** BUSINESS RULES ***
    public Result checkIfRoleExists(String role){
        if (roleRepository.getByName(role) != null){
            return new ErrorResult("Error: Role "+role+" is already exists.");
        }
        return new SuccessResult();
    }

}
