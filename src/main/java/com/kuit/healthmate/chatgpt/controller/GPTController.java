package com.kuit.healthmate.chatgpt.controller;

import com.kuit.healthmate.chatgpt.dto.request.ChatRequest;
import com.kuit.healthmate.chatgpt.dto.response.ChatResponse;
import com.kuit.healthmate.chatgpt.dto.request.RequestDto;
import com.kuit.healthmate.chatgpt.dto.response.LifeStyleToday;
import com.kuit.healthmate.chatgpt.service.GptService;
import com.kuit.healthmate.diagnosis.dto.PostDiagnosisRequest;
import com.kuit.healthmate.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

/*
* 테스트용 컨트롤러입니다.
* */

@RestController
@RequiredArgsConstructor
public class GPTController {
    private final GptService gptService;

    @GetMapping("/chat")
    public ApiResponse<LifeStyleToday> chat(@RequestBody PostDiagnosisRequest request) {

        return new ApiResponse<>(gptService.getPrompt(request));
    }
}
