package com.whatwillieat.meals_history.service;

import com.whatwillieat.meals_history.model.MealHistory;
import com.whatwillieat.meals_history.repository.MealHistoryRepository;
import com.whatwillieat.meals_history.web.dto.MealHistoryRequestDTO;
import com.whatwillieat.meals_history.web.dto.MealHistoryResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MealHistoryService {

    @Autowired
    private MealHistoryRepository mealHistoryRepository;

    public MealHistoryResponseDTO saveMealHistory(MealHistoryRequestDTO dto) {
        MealHistory mealHistory = new MealHistory();
        mealHistory.setUserId(dto.getUserId());
        mealHistory.setMealId(dto.getMealId());
        mealHistory.setRating(dto.getRating());
        mealHistory.setConsumedOn(dto.getConsumedOn());
        mealHistory.setDeleted(false);

        MealHistory savedMeal = mealHistoryRepository.save(mealHistory);
        return mapToResponseDTO(savedMeal);
    }

    public List<MealHistoryResponseDTO> getRecentMeals(UUID userId, LocalDateTime startDate) {
        return mealHistoryRepository.findRecentMeals(userId, startDate)
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    public Optional<MealHistoryResponseDTO> getMealHistoryById(UUID id) {
        return mealHistoryRepository.findByIdAndIsDeletedFalse(id).map(this::mapToResponseDTO);
    }

    public void softDeleteMealHistory(UUID id) {
        mealHistoryRepository.findById(id).ifPresent(mealHistory -> {
            mealHistory.setDeleted(true);
            mealHistoryRepository.save(mealHistory);
        });
    }

    private MealHistoryResponseDTO mapToResponseDTO(MealHistory mealHistory) {
        return new MealHistoryResponseDTO(
                mealHistory.getId(),
                mealHistory.getUserId(),
                mealHistory.getMealId(),
                mealHistory.getRating(),
                mealHistory.getConsumedOn()
        );
    }
}


