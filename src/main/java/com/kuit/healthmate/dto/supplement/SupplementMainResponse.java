package com.kuit.healthmate.dto.supplement;

import java.util.List;
import lombok.Data;

@Data
public class SupplementMainResponse {

    private final List<SupplementResponse> supplements;

    public SupplementMainResponse(List<SupplementResponse> supplements) {
        this.supplements = supplements;
    }
}
