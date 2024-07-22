package com.kuit.healthmate.domain.supplement;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public enum TimeSlot {
    @JsonProperty("BREAKFAST")
    BREAKFAST,
    @JsonProperty("LUNCH")
    LUNCH,
    @JsonProperty("DINNER")
    DINNER;

    @JsonCreator
    TimeSlot() {
    }
}
