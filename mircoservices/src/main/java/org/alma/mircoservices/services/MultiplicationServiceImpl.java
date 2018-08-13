package org.alma.mircoservices.services;

import java.util.List;
import java.util.Optional;

import org.alma.mircoservices.domain.Multiplication;
import org.alma.mircoservices.domain.MultiplicationResultAttempt;
import org.alma.mircoservices.domain.User;
import org.alma.mircoservices.event.EventDispatcher;
import org.alma.mircoservices.event.MultiplicationSolvedEvent;
import org.alma.mircoservices.repository.MultiplicationResultAttemptRepository;
import org.alma.mircoservices.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
public class MultiplicationServiceImpl implements MultiplicationService {

    
    RandomGeneratorService randomGeneratorService;

    MultiplicationResultAttemptRepository attemptRepository;

    UserRepository userRepository;

    EventDispatcher eventDispatcher;

    @Autowired
    public MultiplicationServiceImpl(final RandomGeneratorService randomGeneratorService, 
                                     final MultiplicationResultAttemptRepository attemptRepository,
                                     final UserRepository userRepository,
                                     final EventDispatcher eventDispatcher) {
        this.randomGeneratorService = randomGeneratorService;
        this.userRepository         = userRepository;
        this.attemptRepository      = attemptRepository;
        this.eventDispatcher        = eventDispatcher; 
    }

    @Override
    public Multiplication createRandomMultiplication() {
        int factorA = this.randomGeneratorService.generateRandomFactor();
        int factorB = this.randomGeneratorService.generateRandomFactor();

        return new Multiplication(factorA, factorB);
    }


    @Transactional
    @Override
    public boolean checkAttempt(final MultiplicationResultAttempt attempt) {
        //lit le user en base
        Optional<User> user = userRepository.findByAlias(attempt.getUser().getAlias());
        Assert.isTrue(!attempt.isCorrect(),"You can't send attempt marked as correct");
        // vérifie le résultat
        boolean isCorrect = attempt.getResultAttempt() == attempt.getMultiplication().getFactorA() 
                                                        * attempt.getMultiplication().getFactorB();
        // créé un nouvel enregistrement
        MultiplicationResultAttempt checkAttempt = new MultiplicationResultAttempt(user.orElse(attempt.getUser()),
                                                                                   attempt.getMultiplication(),
                                                                                   attempt.getResultAttempt(),
                                                                                   isCorrect);
        // sauvegarde l'essai
        attemptRepository.save(checkAttempt);

        //envoi l"évènement
        eventDispatcher.send(new MultiplicationSolvedEvent(checkAttempt.getId(), checkAttempt.getUser().getId(), checkAttempt.isCorrect()));
        return isCorrect;
    }
    
    @Override
    public List<MultiplicationResultAttempt> getStatsForUser(String userAlias) {
        return attemptRepository.findTop5ByUserAliasOrderByIdDesc(userAlias);
    }
    
    @Override
    public MultiplicationResultAttempt getMultiplicationResultAttemptById(final Long id) {
        return attemptRepository.findById(id).get();
    }
}