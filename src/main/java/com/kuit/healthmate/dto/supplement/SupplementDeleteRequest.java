package com.kuit.healthmate.dto.supplement;

import com.kuit.healthmate.domain.Status;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SupplementDeleteRequest {

    private final Status status;
}
