package com.whatwillieat.meals_history;

import com.whatwillieat.meals_history.service.MealHistoryService;
import com.whatwillieat.meals_history.web.MealHistoryController;
import com.whatwillieat.meals_history.web.dto.MealHistoryResponseDTO;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MealHistoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // Replace @MockBean with @Mock and @InjectMocks
    @Mock
    private MealHistoryService mealHistoryService;

    @InjectMocks
    private MealHistoryController mealHistoryController;

    @Test
    void getMealHistory_ShouldReturn200_WhenMealExists() throws Exception {
        // Given
        UUID id = UUID.randomUUID();
        UUID mealId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        int rating = 1;
        LocalDateTime date = LocalDateTime.now();
        MealHistoryResponseDTO responseDTO = new MealHistoryResponseDTO(id, mealId, userId, rating, date);
        when(mealHistoryService.getMealHistoryById(mealId)).thenReturn(Optional.of(responseDTO));

        // When & Then
        mockMvc.perform(get("/api/meal-history/" + mealId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(mealId.toString()));
    }

    @Test
    void getMealHistory_ShouldReturn404_WhenMealNotFound() throws Exception {
        // Given
        UUID mealId = UUID.randomUUID();
        when(mealHistoryService.getMealHistoryById(mealId)).thenReturn(Optional.empty());

        // When & Then
        mockMvc.perform(get("/api/meal-history/" + mealId))
                .andExpect(status().isNotFound());
    }
}
