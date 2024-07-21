package com.kuit.healthmate.dto.habit;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Getter
@NoArgsConstructor
public class PatchEditHabitRequest {
    @NotNull(message = "habitId: {Notnull}")
    private Long habitId;

    private String name;

    private String memo;

    @NotBlank(message = "selectedDay: {NotBlank}")
    @Length(min = 7, max = 7, message = "selectedDay: 7자리를 입력해야합니다.")
    private String selectedDay;


    private Long userId;

    private List<SelectedTime> times;
}