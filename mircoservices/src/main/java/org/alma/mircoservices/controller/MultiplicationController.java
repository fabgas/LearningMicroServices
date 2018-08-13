package org.alma.mircoservices.controller;

import org.alma.mircoservices.domain.Multiplication;
import org.alma.mircoservices.domain.MultiplicationResultAttempt;
import org.alma.mircoservices.services.MultiplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/multiplications")
public class MultiplicationController {

    private final MultiplicationService multiplicationService;

    @Autowired
    public MultiplicationController(final  MultiplicationService multiplicationService) {
        this.multiplicationService = multiplicationService;
    }

    @GetMapping("/random")
    Multiplication getRandomMutliplication() {
        return multiplicationService.createRandomMultiplication();
    }

    @GetMapping("/results/{id}")
    MultiplicationResultAttempt getResultById(@PathVariable("id") final Long resultId) {
        return multiplicationService.getMultiplicationResultAttemptById(resultId);
    }
}