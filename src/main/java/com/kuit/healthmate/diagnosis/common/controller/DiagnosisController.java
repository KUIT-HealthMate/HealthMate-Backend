package com.kuit.healthmate.diagnosis.common.controller;


import com.kuit.healthmate.auth.jwt.Jwt;
import com.kuit.healthmate.chatgpt.dto.response.LifeStyleResponse;
import com.kuit.healthmate.chatgpt.dto.response.MealPatternResponse;
import com.kuit.healthmate.chatgpt.dto.response.SleepPatternResponse;
import com.kuit.healthmate.chatgpt.service.GptService;
import com.kuit.healthmate.diagnosis.common.service.DiagnosisService;
import com.kuit.healthmate.diagnosis.dto.DiagnosisResponseDTO;
import com.kuit.healthmate.diagnosis.dto.PostDiagnosisRequest;
import com.kuit.healthmate.global.exception.DiagnosisException;
import com.kuit.healthmate.global.exception.HabitException;
import com.kuit.healthmate.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.concurrent.CompletableFuture;

import static com.kuit.healthmate.global.response.ExceptionResponseStatus.INVALID_DIAGNOSIS_VALUE;
import static com.kuit.healthmate.global.response.ExceptionResponseStatus.INVALID_HABIT_VALUE;
import static com.kuit.healthmate.utils.BindingResultUtils.getErrorMessages;

@RestController
@RequiredArgsConstructor
@RequestMapping("/diagnosis")
public class DiagnosisController {
    private final DiagnosisService diagnosisService;
    private final GptService gptService;
    @PostMapping("")
    public ApiResponse<DiagnosisResponseDTO> saveDiagnosisResult(@Jwt Long userId,@RequestBody PostDiagnosisRequest postDiagnosisRequest, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            throw new DiagnosisException(INVALID_DIAGNOSIS_VALUE, getErrorMessages(bindingResult));
        }
        diagnosisService.saveDiagnosisResult(userId,postDiagnosisRequest); //DB에 저장
        // GPT 호출 비동기 처리
        CompletableFuture<LifeStyleResponse> lifeStyleFuture = gptService.getPromptByLifeStyle(postDiagnosisRequest);
        CompletableFuture<MealPatternResponse> mealPatternFuture = gptService.getPromptByMeal(postDiagnosisRequest);
        CompletableFuture<SleepPatternResponse> sleepPatternFuture = gptService.getPromptBySleep(postDiagnosisRequest);

        // 모든 비동기 작업 완료 대기
        CompletableFuture.allOf(lifeStyleFuture, mealPatternFuture, sleepPatternFuture).join();

        // 비동기 결과 가져오기
        LifeStyleResponse lifeStyleToday = lifeStyleFuture.join();
        MealPatternResponse mealPatternToday = mealPatternFuture.join();
        SleepPatternResponse sleepPatternToday = sleepPatternFuture.join();

        diagnosisService.saveGptResult(userId,lifeStyleToday,mealPatternToday,sleepPatternToday);
        DiagnosisResponseDTO diagnosisResponseDTO = new DiagnosisResponseDTO(LocalDate.now(),lifeStyleToday,mealPatternToday,sleepPatternToday);
        return new ApiResponse<>(diagnosisResponseDTO);
    }

    @GetMapping("/day/{date}")
    public ApiResponse<DiagnosisResponseDTO>  getDayDiagnosisResult(@Jwt Long userId, @PathVariable String date){
        return new ApiResponse<>(diagnosisService.findDayDiagnosisResult(userId,date));
    }
    @GetMapping("/week/{date}")
    public ApiResponse<DiagnosisResponseDTO>  getWeekDiagnosisResult(@Jwt Long userId, @PathVariable String date){
        return new ApiResponse<>(diagnosisService.findWeekDiagnosisResult(userId,date));
    }
    @GetMapping("/month/{date}")
    public ApiResponse<DiagnosisResponseDTO>  getMonthDiagnosisResult(@Jwt Long userId, @PathVariable String date){
        return new ApiResponse<>(diagnosisService.findMonthDiagnosisResult(userId,date));
    }
    //주간, 월간 get 배치처리 마무리하고 구현
}
