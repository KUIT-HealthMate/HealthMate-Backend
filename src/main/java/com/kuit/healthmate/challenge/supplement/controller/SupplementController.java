package com.kuit.healthmate.challenge.supplement.controller;

import com.kuit.healthmate.auth.jwt.Jwt;
import com.kuit.healthmate.challenge.supplement.domain.Supplement;
import com.kuit.healthmate.challenge.supplement.dto.SupplementCheckerRequest;
import com.kuit.healthmate.challenge.supplement.dto.SupplementEditListResponse;
import com.kuit.healthmate.challenge.supplement.dto.SupplementEditResponse;
import com.kuit.healthmate.challenge.supplement.dto.SupplementRegisterRequest;
import com.kuit.healthmate.challenge.supplement.dto.SupplementUpdateRequest;
import com.kuit.healthmate.global.response.ApiResponse;
import com.kuit.healthmate.challenge.supplement.service.SupplementService;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/supplements")
@RequiredArgsConstructor
public class SupplementController {

    private final SupplementService supplementService;

    @PostMapping("/register")
    public ApiResponse<Long> registerSupplement(@Jwt Long userId,
                                                @RequestBody SupplementRegisterRequest supplementRegisterRequest) {
        return new ApiResponse<>(supplementService.registerSupplement(userId, supplementRegisterRequest));
    }

    @PutMapping("/edit/{supplementId}")
    public ApiResponse<Object> updateSupplement(@PathVariable Long supplementId,
                                                @RequestBody SupplementUpdateRequest supplementUpdateRequest) {
        supplementService.updateSupplement(supplementId, supplementUpdateRequest);
        return new ApiResponse<>(null);
    }

    @PutMapping("/delete/{supplementId}")
    public ApiResponse<Object> deleteSupplement(@PathVariable Long supplementId) {
        supplementService.deleteSupplement(supplementId);
        return new ApiResponse<>(null);
    }

    @PutMapping("/check-status/{supplementId}")
    public ApiResponse<Boolean> checkSupplementChecker(@PathVariable Long supplementId,
                                                       @RequestBody SupplementCheckerRequest supplementCheckerRequest) {
        return new ApiResponse<>(
                supplementService.checkSupplementChecker(supplementId, supplementCheckerRequest)
        );
    }

    @GetMapping("/edit")
    public ApiResponse<List<SupplementEditListResponse>> editSupplementList(@Jwt Long userId) {
        List<Supplement> supplementEditList = supplementService.getSupplementEditList(userId);
        List<SupplementEditListResponse> supplementEditListResponses = supplementEditList.stream()
                .map(SupplementEditListResponse::new)
                .toList();
        return new ApiResponse<>(supplementEditListResponses);
    }

    @GetMapping("/edit/{supplementId}") //TODO: 나중에 userId도 확인해서 영양제챌린지를 소유하고 있는 경우에만 접근 가능하게
    public ApiResponse<SupplementEditResponse> editSupplementBase(@Jwt Long userId, @PathVariable Long supplementId) {
        return new ApiResponse<>(
                supplementService.getSupplementEditResponse(supplementId)
        );
    }
}
