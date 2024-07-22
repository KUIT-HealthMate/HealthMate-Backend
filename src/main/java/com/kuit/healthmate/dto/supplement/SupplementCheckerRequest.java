package com.kuit.healthmate.dto.supplement;

import com.kuit.healthmate.domain.supplement.TimeSlot;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SupplementCheckerRequest {
    private final TimeSlot timeSlot;
    private final Boolean status;
}
