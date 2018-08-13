package org.alma.gamification.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public final class GameStats {

    private final Long userId;
    private int score;
    private final List<Badge> badges;
    
    public GameStats() {
        this.userId = 0L;
        this.score = 0;
        this.badges = new ArrayList<>();
    }
    public GameStats(final Long userId, final int score, final List<Badge> badges) {
        this.userId = userId;
        this.score =score;
        this.badges = badges;
   
    }
    /**
     * Factory methods
     */
    public static GameStats emptyStats(final Long userId) {
        return new GameStats(userId,0,new ArrayList<Badge>());
    }

    public List<Badge> getBadges() {
        return Collections.unmodifiableList(badges);
    }
}