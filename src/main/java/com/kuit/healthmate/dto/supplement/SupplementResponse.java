package com.kuit.healthmate.dto.supplement;

import com.kuit.healthmate.domain.supplement.Supplement;
import com.kuit.healthmate.domain.supplement.SupplementRoutine;
import lombok.Data;

@Data
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
