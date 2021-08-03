package com.zoothii.finaljavabackend.core.data_access;

import java.util.List;
import java.util.Optional;

import com.zoothii.finaljavabackend.core.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);

	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);

}
