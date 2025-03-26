package com.whatwillieat.meals_history.web.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MealHistoryRatingRequest {
    @Min(1)
    @Max(10)
    private int rating;
}
