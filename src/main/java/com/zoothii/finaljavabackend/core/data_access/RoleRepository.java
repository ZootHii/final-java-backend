package com.zoothii.finaljavabackend.core.data_access;

import java.util.Optional;

import com.zoothii.finaljavabackend.core.entities.ERole;
import com.zoothii.finaljavabackend.core.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	Optional<Role> findByName(ERole name);
}