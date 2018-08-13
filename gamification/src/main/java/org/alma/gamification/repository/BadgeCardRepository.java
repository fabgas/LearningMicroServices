package org.alma.gamification.repository;

import java.util.List;

import org.alma.gamification.domain.BadgeCard;
import org.springframework.data.repository.CrudRepository;

public interface BadgeCardRepository extends CrudRepository<BadgeCard,Long> {
    /**
     * Renvoie les badges triés par ordre temporelle décroissant
     */
    List<BadgeCard> findByUserIdOrderByBadgeTimestampDesc(final Long userId);
} 