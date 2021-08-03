package com.zoothii.finaljavabackend.core.data_access;

import com.zoothii.finaljavabackend.core.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	Role getByName(String name);
}
