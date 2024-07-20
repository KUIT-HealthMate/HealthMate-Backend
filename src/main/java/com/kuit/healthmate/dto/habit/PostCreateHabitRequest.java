package com.kuit.healthmate.dto.habit;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
public class PostCreateHabitRequest {
    private String name;

    private String memo;

    private String selectedDay;

    private Long userId;

    private List<SelectedTime> times;
}

