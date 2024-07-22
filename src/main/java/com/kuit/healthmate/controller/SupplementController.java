package com.kuit.healthmate.controller;

import com.kuit.healthmate.dto.supplement.SupplementRegisterRequest;
import com.kuit.healthmate.dto.supplement.SupplementResponse;
import com.kuit.healthmate.dto.supplement.SupplementUpdateRequest;
import com.kuit.healthmate.global.response.ApiResponse;
import com.kuit.healthmate.service.SupplementService;
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
    public void registerSupplement(@RequestBody SupplementRegisterRequest supplementRegisterRequest) {
        supplementService.registerSupplement(supplementRegisterRequest);
        return;
    }

    @PutMapping("/{supplementId}")
    public void updateSupplement(@PathVariable Long supplementId, @RequestBody SupplementUpdateRequest supplementUpdateRequest) {
        supplementService.updateSupplement(supplementId, supplementUpdateRequest);
        return;
    }
}
