package com.kuit.healthmate.challenge.supplement.controller;

import com.kuit.healthmate.challenge.supplement.dto.SupplementCheckerRequest;
import com.kuit.healthmate.challenge.supplement.dto.SupplementRegisterRequest;
import com.kuit.healthmate.challenge.supplement.dto.SupplementResponse;
import com.kuit.healthmate.challenge.supplement.dto.SupplementUpdateRequest;
import com.kuit.healthmate.global.exception.SupplementException;
import com.kuit.healthmate.global.exception.UserException;
import com.kuit.healthmate.global.response.ApiResponse;
import com.kuit.healthmate.challenge.supplement.service.SupplementService;
import com.kuit.healthmate.global.response.ExceptionResponseStatus;
import com.kuit.healthmate.user.domain.User;
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


    @GetMapping("/{userId}")
    public ApiResponse<List<SupplementResponse>> getSupplementByUserId(@PathVariable Long userId) {
        return new ApiResponse<>(supplementService.getSupplementChallengesByUserId(userId));
    }

    @PostMapping("")
    public ApiResponse<Long> registerSupplement(@RequestBody SupplementRegisterRequest supplementRegisterRequest) {
        return new ApiResponse<>(supplementService.registerSupplement(supplementRegisterRequest));
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

    @PostMapping("/error")
    public void errorTest() {
        throw new SupplementException(ExceptionResponseStatus.INVALID_SUPPLEMENT_ID);
//        throw new UserException(ExceptionResponseStatus.INVALID_SUPPLEMENT_ID);
    }
}
