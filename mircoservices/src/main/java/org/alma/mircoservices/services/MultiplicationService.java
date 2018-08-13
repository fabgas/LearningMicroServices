package org.alma.mircoservices.services;

import java.util.List;

import org.alma.mircoservices.domain.Multiplication;
import org.alma.mircoservices.domain.MultiplicationResultAttempt;

public interface MultiplicationService {
    
    /**
     * Create a {@link Multiplication} object with two randomly  
     * generated factors
     * @return a Multiplication object with random factors
     */
    Multiplication createRandomMultiplication();

    /**
     * @return true if the attemp matches the result of the multiplication, false otherwise
     */
    boolean checkAttempt(final MultiplicationResultAttempt resultAttempt);

    public List<MultiplicationResultAttempt> getStatsForUser(String userAlias);

    public MultiplicationResultAttempt getMultiplicationResultAttemptById(final Long id);
}