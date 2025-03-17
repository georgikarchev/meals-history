package com.whatwillieat.meals_history.service;

import com.whatwillieat.meals_history.model.MealHistory;
import com.whatwillieat.meals_history.repository.MealHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MealHistoryService {

    @Autowired
    private MealHistoryRepository mealHistoryRepository;

    public MealHistory saveMealHistory(MealHistory mealHistory) {
        return mealHistoryRepository.save(mealHistory);
    }

    public List<MealHistory> getRecentMeals(UUID userId, LocalDateTime startDate) {
        return mealHistoryRepository.findRecentMeals(userId, startDate);
    }

    public Optional<MealHistory> getMealHistoryById(UUID id) {
        return mealHistoryRepository.findByIdAndIsDeletedFalse(id);
    }

    public void softDeleteMealHistory(UUID id) {
        mealHistoryRepository.findById(id).ifPresent(mealHistory -> {
            mealHistory.setDeleted(true);
            mealHistoryRepository.save(mealHistory);
        });
    }
}

