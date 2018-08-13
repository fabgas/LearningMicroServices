package org.alma.mircoservices.repository;

import java.util.List;

import org.alma.mircoservices.domain.MultiplicationResultAttempt;
import org.springframework.data.repository.CrudRepository;

public interface MultiplicationResultAttemptRepository extends CrudRepository<MultiplicationResultAttempt,Long>  {
    List<MultiplicationResultAttempt> findTop5ByUserAliasOrderByIdDesc(String userAlias);
}
