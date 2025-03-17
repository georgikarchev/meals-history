package com.whatwillieat.meals_history.repository;

import com.whatwillieat.meals_history.model.MealHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MealHistoryRepository extends JpaRepository<MealHistory, UUID> {

    @Query("SELECT mh FROM MealHistory mh WHERE mh.userId = :userId AND mh.eatenOn >= :startDate AND mh.isDeleted = false")
    List<MealHistory> findRecentMeals(@Param("userId") UUID userId, @Param("startDate") LocalDateTime startDate);

    Optional<MealHistory> findByIdAndIsDeletedFalse(UUID id);
}
