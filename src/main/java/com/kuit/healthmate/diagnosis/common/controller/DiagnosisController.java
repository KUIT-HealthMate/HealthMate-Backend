package com.kuit.healthmate.diagnosis.common.controller;


import com.kuit.healthmate.chatgpt.dto.response.LifeStyleResponse;
import com.kuit.healthmate.chatgpt.dto.response.MealPatternResponse;
import com.kuit.healthmate.chatgpt.dto.response.SleepPatternResponse;
import com.kuit.healthmate.chatgpt.service.GptService;
import com.kuit.healthmate.diagnosis.common.service.DiagnosisService;
import com.kuit.healthmate.diagnosis.dto.DiagnosisResponseDTO;
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
    private final GptService gptService;
    @PostMapping("")
    public ApiResponse<DiagnosisResponseDTO> saveDiagnosisResult(@RequestBody PostDiagnosisRequest postDiagnosisRequest){
        diagnosisService.saveDiagnosisResult(postDiagnosisRequest); //DB에 저장
        LifeStyleResponse lifeStyleToday = gptService.getPromptByLifeStyle(postDiagnosisRequest);
        MealPatternResponse mealPatternToday =  gptService.getPromptByMeal(postDiagnosisRequest);
        SleepPatternResponse sleepPatternToday =  gptService.getPromptBySleep(postDiagnosisRequest); //GPT 호출
        diagnosisService.saveGptResult(lifeStyleToday,mealPatternToday,sleepPatternToday);
        DiagnosisResponseDTO diagnosisResponseDTO = new DiagnosisResponseDTO(lifeStyleToday,mealPatternToday,sleepPatternToday);
        return new ApiResponse<>(diagnosisResponseDTO);
    }
}
