package org.alma.mircoservices.repository;

import org.alma.mircoservices.domain.Multiplication;
import org.springframework.data.repository.CrudRepository;

public interface MultiplicationRepository extends CrudRepository<Multiplication,Long> {

}