package com.kuit.healthmate.domain.supplement;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Embeddable
@Getter
public class SupplementRoutine {

    protected SupplementRoutine () {

    }

    @Builder
    public SupplementRoutine(int afterMeal, String selectedDay, Boolean breakfast,
                             Boolean lunch, Boolean dinner) {
        this.afterMeal = afterMeal;
        this.selectedDay = selectedDay;
        this.breakfast = breakfast;
        this.lunch = lunch;
        this.dinner = dinner;
    }

    private int afterMeal;  // 식후 30분 일 경우 30, 식전 30분 일 경우 -30

    @NotNull
    @ColumnDefault("0000000")
    @Column(length = 7, nullable = false)
    private String selectedDay; //월,화,수,목,금,토,알 중에 선택한 날짜를 2진수로 표현
    private Boolean breakfast;
    private Boolean lunch;
    private Boolean dinner;
}
