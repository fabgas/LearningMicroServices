package org.alma.gamification.service;

import java.util.List;

import org.alma.gamification.domain.LeaderBoardRow;
import org.alma.gamification.repository.ScoreCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LeaderBoardServiceImpl implements LeaderBoardService {
    
    private ScoreCardRepository scoreCardRepository;
    
    @Autowired
    public LeaderBoardServiceImpl(final ScoreCardRepository scoreCardRepository) {
        this.scoreCardRepository = scoreCardRepository;
    }

    @Override
    public List<LeaderBoardRow> getCurrentLeaderBoard() {
        return this.scoreCardRepository.findFirst10();
    }
}