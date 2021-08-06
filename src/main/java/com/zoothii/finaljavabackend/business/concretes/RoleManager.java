package com.zoothii.finaljavabackend.business.concretes;

import com.zoothii.finaljavabackend.business.abstracts.RoleService;
import com.zoothii.finaljavabackend.core.data_access.RoleDao;
import com.zoothii.finaljavabackend.core.entities.Role;
import com.zoothii.finaljavabackend.core.utulities.results.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleManager implements RoleService {

    private final RoleDao roleDao;

    @Autowired
    public RoleManager(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    public DataResult<List<Role>> getRoles() {
        return new SuccessDataResult<>(roleDao.findAll(), "Roles are successfully listed.");
    }

    @Override
    @PreAuthorize("hasAnyRole('BEN','ADMIN')")
    public Result createRole(Role role) {
        Result resultRoleExists = checkIfRoleExists(role.getName());
        if (resultRoleExists.isSuccess()){
            return new ErrorResult(resultRoleExists.getMessage());
        }

        roleDao.save(role);
        return new SuccessResult("Role "+role.getName()+" is successfully created.");
    }

    @Override
    public DataResult<Role> getRoleByName(String name) {
        Result resultRoleIsNotExists = checkIfRoleIsNotExists(name);
        if (resultRoleIsNotExists.isSuccess()){
            return new ErrorDataResult<>(resultRoleIsNotExists.getMessage());
        }

        return new SuccessDataResult<>(roleDao.getRoleByName(name), "Role returned by name.");
    }


    // *** BUSINESS RULES ***
    @Override
    public Result checkIfRoleIsNotExists(String role) {
        if (roleDao.getRoleByName(role) != null) {
            return new ErrorResult();
        }
        return new SuccessResult("Role " + role + " is not exists.");
    }

    @Override
    public Result checkIfRoleExists(String role) {
        if (roleDao.getRoleByName(role) == null) {
            return new ErrorResult();
        }
        return new SuccessResult("Role " + role + " is exists.");
    }

}
