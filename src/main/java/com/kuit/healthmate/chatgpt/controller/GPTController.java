package com.kuit.healthmate.chatgpt.controller;

import com.kuit.healthmate.chatgpt.dto.response.LifeStyleResponse;
import com.kuit.healthmate.chatgpt.dto.response.MealPatternResponse;
import com.kuit.healthmate.chatgpt.dto.response.SleepPatternResponse;
import com.kuit.healthmate.chatgpt.service.GptService;
import com.kuit.healthmate.diagnosis.dto.PostDiagnosisRequest;
import com.kuit.healthmate.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/*
* 테스트용 컨트롤러입니다.
* */

@RestController
@RequiredArgsConstructor
public class GPTController {
    private final GptService gptService;

    @GetMapping("/chat")
    public ApiResponse<LifeStyleResponse> chat(@RequestBody PostDiagnosisRequest request) {

        return new ApiResponse<>(gptService.getPromptByLifeStyle(request));
    }

    @GetMapping("/chat2")
    public ApiResponse<MealPatternResponse> chat2(@RequestBody PostDiagnosisRequest request) {

        return new ApiResponse<>(gptService.getPromptByMeal(request));
    }

    @GetMapping("/chat3")
    public ApiResponse<SleepPatternResponse> chat3(@RequestBody PostDiagnosisRequest request) {

        return new ApiResponse<>(gptService.getPromptBySleep(request));
    }
}
