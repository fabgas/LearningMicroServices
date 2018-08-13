package org.alma.gamification.client;

import org.alma.gamification.client.dto.MultiplicationResultAttempt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
class MultiplicationResultAttemptClientImpl implements MultiplicationResultAttemptClient {

    private final RestTemplate restTemplate;
    private final String multiplicationsHost;

    @Autowired
    public MultiplicationResultAttemptClientImpl(final RestTemplate restTemplate,@Value("${multiplicationHost}") final String multiplicationHost) {
        this.restTemplate = restTemplate;
        this.multiplicationsHost = multiplicationHost;
    }

    @Override
    public MultiplicationResultAttempt retreiveMultiplicationResultAttemptbyId(final Long multiplicationResultAttemptId) {
        return restTemplate.getForObject(multiplicationsHost + "/multiplications/results/" +
            multiplicationResultAttemptId,MultiplicationResultAttempt.class
        );
    }

}