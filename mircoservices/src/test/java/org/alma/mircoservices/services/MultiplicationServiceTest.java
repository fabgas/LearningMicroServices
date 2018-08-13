package org.alma.mircoservices.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.verify;
import java.util.Optional;

import org.alma.mircoservices.domain.Multiplication;
import org.alma.mircoservices.domain.MultiplicationResultAttempt;
import org.alma.mircoservices.domain.User;
import org.alma.mircoservices.event.EventDispatcher;
import org.alma.mircoservices.repository.MultiplicationResultAttemptRepository;
import org.alma.mircoservices.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


public class MultiplicationServiceTest {
    @Mock
    private RandomGeneratorService randomGeneratorService;

    @Mock
    private MultiplicationResultAttemptRepository multiplicationResultAttemptRepository;
    
    @Mock
    private UserRepository userRepository;
    
    @Mock
    private EventDispatcher eventDispatcher;
    private MultiplicationService multiplicationService;
    
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        multiplicationService = new MultiplicationServiceImpl(randomGeneratorService,multiplicationResultAttemptRepository,userRepository,eventDispatcher);
        
    }
    @Test
    public void createRandomMultiplicationTest() {
        // given(...) return 50 and then 30
        given(randomGeneratorService.generateRandomFactor()).willReturn(50,30);

        //When 
        Multiplication multiplication = multiplicationService.createRandomMultiplication();

        // then
        assertThat(multiplication.getFactorA()).isEqualTo(50);
        assertThat(multiplication.getFactorB()).isEqualTo(30);
    }

    @Test
    public void checkCorrectAttemptTest() {
        Multiplication multiplication = new Multiplication(50, 60);
        User user = new User("john_doe");
        MultiplicationResultAttempt attempt = new MultiplicationResultAttempt(user, multiplication, 3000,false);
        MultiplicationResultAttempt verifiedAttempt = new
        MultiplicationResultAttempt(
        user, multiplication, 3000, true);
        given(userRepository.findByAlias("john_doe")).
        willReturn(Optional.empty());
        // when
        boolean attemptResult = multiplicationService. checkAttempt(attempt);
        // then
        assertThat(attemptResult).isTrue();
        verify(multiplicationResultAttemptRepository).save(verifiedAttempt);
        }
    @Test
    public void checkWrongAttemptTest() {
        Multiplication multiplication = new Multiplication(50, 60);
        User user = new User("john_doe");
        MultiplicationResultAttempt attempt = new MultiplicationResultAttempt(user, multiplication, 3010, false);
        given(userRepository.findByAlias("john_doe")).
        willReturn(Optional.empty());
        boolean attemptResult = multiplicationService.checkAttempt(attempt);
        // then
        assertThat(attemptResult).isFalse();
        verify(multiplicationResultAttemptRepository).save(attempt);
    }
}