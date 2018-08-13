package org.alma.gamification.service;

import org.alma.gamification.domain.GameStats;

public interface GameService {
    
    GameStats newAttemptForUser(Long userId, Long attemptId,boolean correct);
    
    GameStats retrieveStatsForUser(Long userId);

}