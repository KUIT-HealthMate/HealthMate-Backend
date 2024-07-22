package com.kuit.healthmate.controller;

import com.kuit.healthmate.dto.supplement.SupplementCheckerRequest;
import com.kuit.healthmate.dto.supplement.SupplementDeleteRequest;
import com.kuit.healthmate.dto.supplement.SupplementRegisterRequest;
import com.kuit.healthmate.dto.supplement.SupplementResponse;
import com.kuit.healthmate.dto.supplement.SupplementUpdateRequest;
import com.kuit.healthmate.global.response.ApiResponse;
import com.kuit.healthmate.service.SupplementService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

@Slf4j
@RestController
@RequestMapping("/supplements")
@RequiredArgsConstructor
public class SupplementController {

    private final SupplementService supplementService;


    @GetMapping("/{userId}")
    public ApiResponse<List<SupplementResponse>> getSupplementByUserId(@PathVariable Long userId) {
        return new ApiResponse<>(supplementService.getSupplementChallengesByUserId(userId));
    }

    @PostMapping("/register")
    public ApiResponse<Long> registerSupplement(@RequestBody SupplementRegisterRequest supplementRegisterRequest) {
        return new ApiResponse<>(supplementService.registerSupplement(supplementRegisterRequest));
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{supplementId}")
    public void updateSupplement(@PathVariable Long supplementId,
                                 @RequestBody SupplementUpdateRequest supplementUpdateRequest) {
        supplementService.updateSupplement(supplementId, supplementUpdateRequest);
        return;
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/delete/{supplementId}")
    public void deleteSupplement(@PathVariable Long supplementId,
                                 @RequestBody SupplementDeleteRequest supplementDeleteRequest) {
        supplementService.deleteSupplement(supplementId, supplementDeleteRequest);
    }

    @PutMapping("/check-status/{supplementId}")
    public ApiResponse<Boolean> checkSupplementChecker(@PathVariable Long supplementId,
                                       @RequestBody SupplementCheckerRequest supplementCheckerRequest) {
        return new ApiResponse<>(
                supplementService.checkSupplementChecker(supplementId, supplementCheckerRequest)
        );
    }
}
