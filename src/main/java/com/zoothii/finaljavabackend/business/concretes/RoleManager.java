package com.zoothii.finaljavabackend.business.concretes;

import com.zoothii.finaljavabackend.business.abstracts.RoleService;
import com.zoothii.finaljavabackend.core.data_access.RoleDao;
import com.zoothii.finaljavabackend.core.entities.Role;
import com.zoothii.finaljavabackend.core.utulities.constants.Messages;
import com.zoothii.finaljavabackend.core.utulities.constants.Roles;
import com.zoothii.finaljavabackend.core.utulities.results.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@CacheConfig(cacheNames = {"roles"})
public class RoleManager implements RoleService {

    private final RoleDao roleDao;

    @Autowired
    public RoleManager(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    @Cacheable(key = "'roles-cache'")
    public DataResult<List<Role>> getRoles() throws InterruptedException {
        Thread.sleep(1000);
        return new SuccessDataResult<>(roleDao.findAll(), Messages.successGetRoles);
    }

    @Override
    @PreAuthorize("hasAnyRole('BEN','ADMIN')")
    /*@CachePut(condition = "#result.success") // not used because puts as Result we need DataResult*/
    @CacheEvict(key = "'roles-cache'", condition = "#result.success") // result.success condition works
    public Result createRole(Role role) {
        var resultRoleExists = checkIfRoleExists(role.getName());
        if (resultRoleExists.isSuccess()) {
            return new ErrorResult(resultRoleExists.getMessage());
        }

        roleDao.save(role);
        return new SuccessResult("Role " + role.getName() + " is successfully created.");
    }

    @Override
    //@PreAuthorize("hasAnyRole('BEN','ADMIN')")
    @CacheEvict(key = "'roles-cache'", condition = "#result.success")
    public Result deleteRole(Role role) {
        var resultRoleExists = checkIfRoleExists(role.getName());
        if (!resultRoleExists.isSuccess()) {
            return new ErrorResult(resultRoleExists.getMessage());
        }

        roleDao.delete(role);
        return new SuccessResult("Role " + role.getName() + " is successfully deleted.");
    }

    @Override
    public DataResult<Role> getRoleByName(String name) {
        var resultRoleExists = checkIfRoleExists(name);
        if (!resultRoleExists.isSuccess()) {
            return new ErrorDataResult<>(resultRoleExists.getMessage());
        }

        return new SuccessDataResult<>(roleDao.getRoleByName(name), Messages.successGetRoleByName);
    }


    // *** BUSINESS RULES ***
//    @Override
//    public Result checkIfRoleIsNotExists(String role) {
//        if (roleDao.getRoleByName(role) != null) {
//            return new ErrorResult("Role " + role + " is exists.");
//        }
//        return new SuccessResult("Role " + role + " is not exists.");
//    }

    @Override
    public Result checkIfRoleExists(String role) {
        if (roleDao.getRoleByName(role) == null) {
            return new ErrorResult("Role " + role + " is not exists.");
        }
        return new SuccessResult("Role " + role + " is exists.");
    }

    @Override
    public Result createDefaultRoleIfNotExists(String defaultRole) {
        //var defaultRole = Roles.ROLE_USER;
        if (roleDao.getRoleByName(defaultRole) != null){
            return new ErrorResult("Default role " + defaultRole + " is already exists.");
        }

        roleDao.save(new Role(0, defaultRole));
        return new SuccessResult("Default role " + defaultRole + " is successfully created.");
    }
}
