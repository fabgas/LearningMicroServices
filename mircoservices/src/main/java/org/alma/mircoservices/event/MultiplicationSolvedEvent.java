
package org.alma.mircoservices.event;

import java.io.Serializable;

import org.alma.mircoservices.domain.Multiplication;

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
}