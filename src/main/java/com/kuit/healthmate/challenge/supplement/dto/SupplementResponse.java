package com.kuit.healthmate.challenge.supplement.dto;

import com.kuit.healthmate.challenge.supplement.domain.Supplement;
import com.kuit.healthmate.challenge.supplement.domain.SupplementRoutine;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SupplementResponse {

    public SupplementResponse(Supplement supplement) {
        SupplementRoutine supplementRoutine = supplement.getSupplementRoutine();
        this.name = supplement.getName();
        this.afterMeal = supplementRoutine.getAfterMeal();
        this.breakfast = supplementRoutine.getBreakfast();
        this.lunch = supplementRoutine.getLunch();
        this.dinner = supplementRoutine.getDinner();
    }

    private final String name;
    private final int afterMeal;
    private final Boolean breakfast;
    private final Boolean lunch;
    private final Boolean dinner;
}
