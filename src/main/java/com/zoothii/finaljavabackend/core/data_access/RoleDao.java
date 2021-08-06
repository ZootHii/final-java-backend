package com.zoothii.finaljavabackend.core.data_access;

import com.zoothii.finaljavabackend.core.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleDao extends JpaRepository<Role, Long> {
    Role getRoleByName(String name);
}
