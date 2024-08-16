package com.kuit.healthmate.diagnosis.dto.response.day;

import com.kuit.healthmate.chatgpt.dto.response.MealPatternResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MealPatternResponseWithAverage {
    private Double mealAverage;
    private Double mealRegularityAverage;
    private Double mealNutritionAverage;
    private Double mealAlcoholAverage;
    private MealPatternResponse mealPatternResponse;

    public MealPatternResponseWithAverage(Double mealAverage, Double  mealRegularityAverage, Double mealNutritionAverage, Double mealAlcoholAverage, MealPatternResponse mealPatternResponse){
        this.mealAverage = mealAverage;
        this.mealRegularityAverage = mealRegularityAverage;
        this.mealNutritionAverage = mealNutritionAverage;
        this.mealAlcoholAverage = mealAlcoholAverage;
        this.mealPatternResponse = mealPatternResponse;
    }
}
