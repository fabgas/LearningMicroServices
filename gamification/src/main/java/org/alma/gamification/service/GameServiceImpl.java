package org.alma.gamification.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.alma.gamification.client.MultiplicationResultAttemptClient;
import org.alma.gamification.client.dto.MultiplicationResultAttempt;
import org.alma.gamification.domain.Badge;
import org.alma.gamification.domain.BadgeCard;
import org.alma.gamification.domain.GameStats;
import org.alma.gamification.domain.ScoreCard;
import org.alma.gamification.repository.BadgeCardRepository;
import org.alma.gamification.repository.ScoreCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class GameServiceImpl implements GameService {
    private ScoreCardRepository scoreCardRepository;
    private BadgeCardRepository badgeCardRepository;
    private MultiplicationResultAttemptClient attemptClient;
    public static final int LUCKY_NUMBER = 42;

    @Autowired
    GameServiceImpl(final ScoreCardRepository scoreCardRepository,
                    final BadgeCardRepository badgeCardRepository,
                    final MultiplicationResultAttemptClient attemptClient) {
        this.badgeCardRepository = badgeCardRepository;
        this.scoreCardRepository = scoreCardRepository;
        this.attemptClient = attemptClient;
    }
    
    public GameStats newAttemptForUser(final Long userId,
                                       final Long attemptId,
                                       final boolean correct    ) {
        // points uniquement si correct (v1)    
        if (correct) {
            //enregistrement de l'essai
            ScoreCard scoreCard = new ScoreCard(userId,attemptId);
            scoreCardRepository.save(scoreCard);
            log.info("User with id {} scored {} points for attempt id {}",userId,scoreCard.getScore(),attemptId);
            //traitement des badges
            List<BadgeCard> badgeCards = processForBadges(userId,attemptId);
            // lit les badges et les transforme en une collection de enum badge
            return new GameStats(userId,scoreCard.getScore(),badgeCards.stream().map(BadgeCard::getBadge).collect(Collectors.toList()));
        }
        return GameStats.emptyStats(userId);
    }

    private List<BadgeCard> processForBadges(final Long userId, final Long attemptId) {
        List<BadgeCard> badgeCards = new ArrayList<>();
        int totalScore = scoreCardRepository.getTotalScoreForUser(userId);
        log.info("New score for user {} is{}",userId,totalScore);
        // historique des scores
        List<ScoreCard> scoreCardList = scoreCardRepository.findByUserIdOrderByScoreTimestampDesc(userId);
        // historique des badges
        List<BadgeCard> badgeCardList = badgeCardRepository.findByUserIdOrderByBadgeTimestampDesc(userId);    
        // les badges dépendent du score
        checkAndGiveBadgeOnScore(badgeCardList,Badge.BRONZE_MULTIPLICATOR,totalScore,100,userId).ifPresent(badgeCards::add);

        checkAndGiveBadgeOnScore(badgeCardList,Badge.SILVER_MULTIPLICATOR,totalScore,500,userId).ifPresent(badgeCards::add);
    
        checkAndGiveBadgeOnScore(badgeCardList,Badge.GOLD_MULTIPLICATOR,totalScore,999,userId).ifPresent(badgeCards::add);
        
        if (scoreCardList.size() == 1 && !containsBadge(badgeCardList,Badge.FIRST_WON)) {
            BadgeCard firstWonBadge = giveBadgeToUser(Badge.FIRST_WON,userId);
            badgeCards.add(firstWonBadge);
        }
        // récupération des infos sur l'évènement
        MultiplicationResultAttempt attempt = attemptClient.retreiveMultiplicationResultAttemptbyId(attemptId);
        if (!containsBadge(badgeCardList, Badge.LUCKY_NUMBER) && 
        (LUCKY_NUMBER == attempt.getMultiplicationFactorA() ||LUCKY_NUMBER == attempt.getMultiplicationFactorB())){
            BadgeCard luckyNumberBadge = giveBadgeToUser(Badge.LUCKY_NUMBER, userId);
            badgeCards.add(luckyNumberBadge);
        }
        return badgeCards;
    }

    public GameStats retrieveStatsForUser(final Long userId) {
        int score = scoreCardRepository.getTotalScoreForUser(userId);
        List<BadgeCard> badgeCards = badgeCardRepository.findByUserIdOrderByBadgeTimestampDesc(userId);
        return new GameStats(userId,score,badgeCards.stream().map(BadgeCard::getBadge).collect(Collectors.toList()));
    }

    private Optional<BadgeCard> checkAndGiveBadgeOnScore(final List<BadgeCard> badgeCards,
                                                         final Badge badge,
                                                         final int score,
                                                         final int scoreThreshold,
                                                         final Long userId) {
        if (score>scoreThreshold && !containsBadge(badgeCards,badge)) {
            return Optional.of(giveBadgeToUser(badge,userId));
        }
        return Optional.empty();
    }

    private boolean containsBadge(final List<BadgeCard> badgeCards, final Badge badge) {
        return badgeCards.stream().anyMatch(b->b.getBadge().equals(badge));
    }

    private BadgeCard giveBadgeToUser(final Badge badge, final Long userId) {
        BadgeCard badgeCard = new BadgeCard(userId,badge);
        badgeCardRepository.save(badgeCard);
        log.info("User with id {} won a new badge :{}",userId,badge);
        return badgeCard;
    }
}