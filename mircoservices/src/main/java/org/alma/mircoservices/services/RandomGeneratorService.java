package org.alma.mircoservices.services;

public interface RandomGeneratorService {
    /**
     * @return a random generator factor between 11 and 99
     */
    int generateRandomFactor();
}