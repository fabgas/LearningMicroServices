package org.alma.gamification.event;

import org.alma.gamification.service.GameService;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
class EventHandler {
    private GameService gameService;

    @Autowired
    EventHandler(final GameService gameService) {
        this.gameService = gameService;
    }

    @RabbitListener(queues="${multiplication.queue}")
    void handlerMultiplicationSolved(final MultiplicationSolvedEvent event) {
        log.info("Multiplication Solved Event received: {}",event.getMultiplicationResultAttemptId());
        try {
            gameService.newAttemptForUser(event.getUserId(),event.getMultiplicationResultAttemptId(),event.isCorrect());
        } catch(final Exception e) {
            log.error("Error when trying to process MultiplicationSolvedEvent", e);
            throw new AmqpRejectAndDontRequeueException(e);
        }
    }
}