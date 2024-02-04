package org.entitylabs.mtls.authentication.repository;

import java.util.Optional;

import org.entitylabs.mtls.authentication.domain.FunctionalUser;
import org.springframework.data.repository.CrudRepository;

public interface FunctionalUserRepository extends CrudRepository<FunctionalUser, String>{

	Optional<FunctionalUser> findByUsername(final String username);
}
