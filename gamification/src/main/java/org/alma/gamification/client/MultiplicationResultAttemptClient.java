package org.alma.gamification.client;

import org.alma.gamification.client.dto.MultiplicationResultAttempt;

public interface MultiplicationResultAttemptClient {

    MultiplicationResultAttempt retreiveMultiplicationResultAttemptbyId(final Long multiplicationId);

}