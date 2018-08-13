package org.alma.gamification.controller;

import java.util.List;

import org.alma.gamification.domain.LeaderBoardRow;
import org.alma.gamification.service.LeaderBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/leaders")
class LeaderBoardController {
     
    private final LeaderBoardService leaderBoardService;
    
    @Autowired
    public LeaderBoardController(final LeaderBoardService leaderBoardService) {
        this.leaderBoardService = leaderBoardService;
    }

    @GetMapping
    public List<LeaderBoardRow> getLeaderBoardRow() {
            return this.leaderBoardService.getCurrentLeaderBoard();
    }

    
}