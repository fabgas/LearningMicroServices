package org.alma.gamification.repository;

import java.util.List;

import org.alma.gamification.domain.LeaderBoardRow;
import org.alma.gamification.domain.ScoreCard;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ScoreCardRepository extends CrudRepository<ScoreCard,Long> {
    @Query("SELECT SUM(s.score) FROM org.alma.gamification.domain.ScoreCard s WHERE s.userId = :userId GROUP BY s.userId")
    int getTotalScoreForUser(@Param("userId") final Long userId);

    @Query("SELECT NEW org.alma.gamification.domain.LeaderBoardRow(s.userId,SUM(s.score)) " + 
     "FROM org.alma.gamification.domain.ScoreCard s " +
     "GROUP BY s.userId ORDER BY SUM(s.score) DESC")
    List<LeaderBoardRow> findFirst10();

    List<ScoreCard> findByUserIdOrderByScoreTimestampDesc(final Long userId);

}