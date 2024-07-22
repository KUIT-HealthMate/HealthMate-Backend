package com.kuit.healthmate.dto.supplement;

import com.kuit.healthmate.domain.supplement.TimeSlot;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@NoArgsConstructor
public class SupplementCheckerRequest {

    private TimeSlot timeSlot;
}
