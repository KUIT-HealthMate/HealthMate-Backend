package com.kuit.healthmate.diagnosis.common.controller;


import com.kuit.healthmate.chatgpt.dto.response.LifeStyleToday;
import com.kuit.healthmate.chatgpt.dto.response.MealPatternToday;
import com.kuit.healthmate.chatgpt.dto.response.SleepPatternToday;
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
    @Deprecated
    @PostMapping("")
    public ApiResponse<DiagnosisResponseDTO> saveDiagnosisResult(@RequestBody PostDiagnosisRequest postDiagnosisRequest){
        diagnosisService.saveDiagnosisResult(postDiagnosisRequest); //DB에 저장
        LifeStyleToday lifeStyleToday = gptService.getPromptByLifeStyle(postDiagnosisRequest);
        MealPatternToday mealPatternToday =  gptService.getPromptByMeal(postDiagnosisRequest);
        SleepPatternToday sleepPatternToday =  gptService.getPromptBySleep(postDiagnosisRequest); //GPT 호출
        diagnosisService.saveGptResult(lifeStyleToday,mealPatternToday,sleepPatternToday);
        DiagnosisResponseDTO diagnosisResponseDTO = new DiagnosisResponseDTO(lifeStyleToday,mealPatternToday,sleepPatternToday);
        return new ApiResponse<>(diagnosisResponseDTO);
    }
}
