package org.alma.gamification.service;

import java.util.List;

import org.alma.gamification.domain.LeaderBoardRow;

public interface LeaderBoardService {
    List<LeaderBoardRow> getCurrentLeaderBoard();
}