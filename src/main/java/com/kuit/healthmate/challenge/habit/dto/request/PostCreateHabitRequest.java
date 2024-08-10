package com.kuit.healthmate.challenge.habit.dto.request;


import com.kuit.healthmate.challenge.habit.dto.SelectedTime;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.List;


@Getter
@NoArgsConstructor
public class PostCreateHabitRequest {
    @NotBlank(message = "name: {NotBlank}")
    private String name;

    @NotBlank(message = "selectedDay: {NotBlank}")
    @Length(min = 7, max = 7, message = "selectedDay: 7자리를 입력해야합니다.")
    private String selectedDay;

    private List<SelectedTime> times;
}

