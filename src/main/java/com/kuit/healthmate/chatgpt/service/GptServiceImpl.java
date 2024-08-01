package com.kuit.healthmate.chatgpt.service;

import com.kuit.healthmate.chatgpt.dto.request.ChatRequest;
import com.kuit.healthmate.chatgpt.dto.response.ChatResponse;
import com.kuit.healthmate.chatgpt.dto.response.LifeStyleToday;
import com.kuit.healthmate.chatgpt.dto.response.MealPatternToday;
import com.kuit.healthmate.chatgpt.util.formatter.LifeStyleTodayFormatter;
import com.kuit.healthmate.chatgpt.util.formatter.MealPatternTodayFormatter;
import com.kuit.healthmate.chatgpt.util.formatter.SleepPatternTodayFormatter;
import com.kuit.healthmate.chatgpt.util.parser.LifeStyleTodayParser;
import com.kuit.healthmate.diagnosis.dto.PostDiagnosisRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
@RequiredArgsConstructor
public class GptServiceImpl implements GptService{
    @Qualifier("openaiRestTemplate")
    private final RestTemplate restTemplate;

    @Value("${openai.model}")
    private String model;

    @Value("${openai.api.url}")
    private String apiUrl;

    @Override
    public LifeStyleToday getPrompt(PostDiagnosisRequest requestDto) {
        LifeStyleTodayParser lifeStyleTodayParser = new LifeStyleTodayParser();
        LifeStyleTodayFormatter lifeStyleTodayFormatter = new LifeStyleTodayFormatter();

        String message = lifeStyleTodayFormatter.formatResponse(requestDto);

        log.info(message);

        ChatRequest request = new ChatRequest(model, message);

        ChatResponse response = restTemplate.postForObject(apiUrl, request, ChatResponse.class);

        log.info(response.getChoices().get(0).getMessage().getContent());

        if (response == null || response.getChoices() == null || response.getChoices().isEmpty()) {
            return null;
        }

        return lifeStyleTodayParser.parse(response.getChoices().get(0).getMessage().getContent());
    }

    @Override
    public String getPromptByMeal(PostDiagnosisRequest requestDto) {
        MealPatternTodayFormatter mealPatternTodayFormatter = new MealPatternTodayFormatter();

        String message = mealPatternTodayFormatter.formatResponse(requestDto);

        log.info(message);

        ChatRequest request = new ChatRequest(model, message);

        ChatResponse response = restTemplate.postForObject(apiUrl, request, ChatResponse.class);

        log.info(response.getChoices().get(0).getMessage().getContent());

        if (response == null || response.getChoices() == null || response.getChoices().isEmpty()) {
            return null;
        }

        return response.getChoices().get(0).getMessage().getContent();

    }

    @Override
    public String getPromptBySleep(PostDiagnosisRequest requestDto) {
        SleepPatternTodayFormatter sleepPatternTodayFormatter = new SleepPatternTodayFormatter();

        String message = sleepPatternTodayFormatter.formatResponse(requestDto);

        log.info(message);

        ChatRequest request = new ChatRequest(model, message);

        ChatResponse response = restTemplate.postForObject(apiUrl, request, ChatResponse.class);

        log.info(response.getChoices().get(0).getMessage().getContent());

        if (response == null || response.getChoices() == null || response.getChoices().isEmpty()) {
            return null;
        }

        return response.getChoices().get(0).getMessage().getContent();
    }
}
