package com.kuit.healthmate.diagnosis.common.controller;


import com.kuit.healthmate.diagnosis.common.service.DiagnosisService;
import com.kuit.healthmate.diagnosis.dto.PostDiagnosisRequest;
import com.kuit.healthmate.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/diagnosis")
public class DiagnosisController {
    private final DiagnosisService diagnosisService;

    @PostMapping("")
    public ApiResponse<Boolean> saveDiagnosisResult(@RequestBody PostDiagnosisRequest postDiagnosisRequest){
        return new ApiResponse<>(diagnosisService.saveDiagnosisResult(postDiagnosisRequest));
    }
}
