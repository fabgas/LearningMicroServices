package org.alma.mircoservices.repository;

import java.util.Optional;

import org.alma.mircoservices.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Long> {
    Optional<User>  findByAlias(final String alias);
    
}