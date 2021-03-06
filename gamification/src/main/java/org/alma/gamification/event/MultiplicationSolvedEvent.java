
package org.alma.gamification.event;

import java.io.Serializable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
/**
 * Event that models the fact that a {@link Multiplication} has been solved 
 * in the system.
 */
@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class MultiplicationSolvedEvent implements Serializable {
    private final Long multiplicationResultAttemptId;
    private final Long userId;
    private final boolean correct;

    MultiplicationSolvedEvent() {
        this.multiplicationResultAttemptId =-1L;
        this.userId =-1L;
        this.correct = false;
    }
}