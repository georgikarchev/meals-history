package com.whatwillieat.meals_history.web;

import com.whatwillieat.meals_history.service.MealHistoryService;
import com.whatwillieat.meals_history.web.dto.MealHistoryRequestDTO;
import com.whatwillieat.meals_history.web.dto.MealHistoryResponseDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("${app.API_V1_BASE_URL}/meal-history")
public class MealHistoryController {

    private final MealHistoryService mealHistoryService;

    @Autowired
    public MealHistoryController(MealHistoryService mealHistoryService) {
        this.mealHistoryService = mealHistoryService;
    }

    @PostMapping
    public ResponseEntity<MealHistoryResponseDTO> addMealToHistory(@RequestBody @Valid MealHistoryRequestDTO mealHistoryDTO) {
        MealHistoryResponseDTO savedMeal = mealHistoryService.saveMealHistory(mealHistoryDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMeal);
    }

    @GetMapping("/recent")
    public ResponseEntity<List<MealHistoryResponseDTO>> getRecentMeals(
            @RequestParam UUID userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate) {
        List<MealHistoryResponseDTO> meals = mealHistoryService.getRecentMeals(userId, startDate);
        return ResponseEntity.ok(meals);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MealHistoryResponseDTO> getMealHistoryById(@PathVariable UUID id) {
        return mealHistoryService.getMealHistoryById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> softDeleteMealHistory(@PathVariable UUID id) {
        mealHistoryService.softDeleteMealHistory(id);
        return ResponseEntity.noContent().build();
    }
}