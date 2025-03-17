package com.whatwillieat.meals_history;

import com.whatwillieat.meals_history.model.MealHistory;
import com.whatwillieat.meals_history.repository.MealHistoryRepository;
import com.whatwillieat.meals_history.service.MealHistoryService;
import com.whatwillieat.meals_history.web.dto.MealHistoryResponseDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class MealHistoryServiceTest {

    @Mock
    private MealHistoryRepository mealHistoryRepository;

    @InjectMocks
    private MealHistoryService mealHistoryService;

    @Test
    void getMealHistoryById_ShouldReturnMealHistory_WhenFound() {
        // Given
        UUID id = UUID.randomUUID();
        UUID mealId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        int rating = 1;
        MealHistory mealHistory = MealHistory.builder()
                .id(id)
                .mealId(mealId)
                .userId(userId)
                .rating(rating)
                .build();
        when(mealHistoryRepository.findByIdAndIsDeletedFalse(mealId)).thenReturn(Optional.of(mealHistory));

        // When
        Optional<MealHistoryResponseDTO> result = mealHistoryService.getMealHistoryById(mealId);

        // Then
        assertTrue(result.isPresent());
        assertEquals(id, result.get().getId());
    }

    @Test
    void getMealHistoryById_ShouldReturnEmptyOptional_WhenNotFound() {
        // Given
        UUID mealId = UUID.randomUUID();
        when(mealHistoryRepository.findByIdAndIsDeletedFalse(mealId)).thenReturn(Optional.empty());

        // When
        Optional<MealHistoryResponseDTO> result = mealHistoryService.getMealHistoryById(mealId);

        // Then
        assertTrue(result.isEmpty());
    }
}