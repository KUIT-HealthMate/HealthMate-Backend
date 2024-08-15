package com.kuit.healthmate.chatgpt.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kuit.healthmate.chatgpt.dto.request.ChatRequest;
import com.kuit.healthmate.chatgpt.dto.response.ChatResponse;
import com.kuit.healthmate.chatgpt.dto.response.LifeStyleResponse;
import com.kuit.healthmate.chatgpt.dto.response.MealPatternResponse;
import com.kuit.healthmate.chatgpt.dto.response.SleepPatternResponse;
import com.kuit.healthmate.chatgpt.util.formatter.day.LifeStyleTodayFormatter;
import com.kuit.healthmate.chatgpt.util.formatter.day.MealPatternTodayFormatter;
import com.kuit.healthmate.chatgpt.util.formatter.day.SleepPatternTodayFormatter;
import com.kuit.healthmate.chatgpt.util.parser.LifeStyleTodayParser;
import com.kuit.healthmate.chatgpt.util.parser.MealPatternTodayParser;
import com.kuit.healthmate.chatgpt.util.parser.SleepPatternParser;
import com.kuit.healthmate.diagnosis.dto.PostDiagnosisRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;

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
    public CompletableFuture<LifeStyleResponse> getPromptByLifeStyle(PostDiagnosisRequest requestDto) {
        LifeStyleTodayParser lifeStyleTodayParser = new LifeStyleTodayParser();
        LifeStyleTodayFormatter lifeStyleTodayFormatter = new LifeStyleTodayFormatter();

        String message = lifeStyleTodayFormatter.formatResponse(requestDto);

        log.info(message);

        ChatRequest request = new ChatRequest(model, message);

        ChatResponse response = restTemplate.postForObject(apiUrl, request, ChatResponse.class);

        log.info(response.getChoices().get(0).getMessage().getContent());

        if (response == null || response.getChoices() == null || response.getChoices().isEmpty()) {
            return CompletableFuture.completedFuture(null);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        LifeStyleResponse lifeStyleResponse;
        try {
            lifeStyleResponse = objectMapper.readValue(response.getChoices().get(0).getMessage().getContent(), LifeStyleResponse.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return CompletableFuture.completedFuture(lifeStyleResponse);
    }
    @Override
    public CompletableFuture<MealPatternResponse> getPromptByMeal(PostDiagnosisRequest requestDto) {
        MealPatternTodayFormatter mealPatternTodayFormatter = new MealPatternTodayFormatter();
        MealPatternTodayParser mealPatternTodayParser = new MealPatternTodayParser();

        String message = mealPatternTodayFormatter.formatResponse(requestDto);

        log.info(message);

        ChatRequest request = new ChatRequest(model, message);

        ChatResponse response = restTemplate.postForObject(apiUrl, request, ChatResponse.class);

        log.info(response.getChoices().get(0).getMessage().getContent());

        if (response == null || response.getChoices() == null || response.getChoices().isEmpty()) {
            return CompletableFuture.completedFuture(null);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        MealPatternResponse mealPatternResponse;
        try {
            mealPatternResponse = objectMapper.readValue(response.getChoices().get(0).getMessage().getContent(), MealPatternResponse.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return CompletableFuture.completedFuture(mealPatternResponse);

    }

    @Override
    public CompletableFuture<SleepPatternResponse> getPromptBySleep(PostDiagnosisRequest requestDto) {
        SleepPatternTodayFormatter sleepPatternTodayFormatter = new SleepPatternTodayFormatter();
        SleepPatternParser sleepPatternParser = new SleepPatternParser();

        String message = sleepPatternTodayFormatter.formatResponse(requestDto);

        log.info(message);

        ChatRequest request = new ChatRequest(model, message);

        ChatResponse response = restTemplate.postForObject(apiUrl, request, ChatResponse.class);

        log.info(response.getChoices().get(0).getMessage().getContent());

        if (response == null || response.getChoices() == null || response.getChoices().isEmpty()) {
            return CompletableFuture.completedFuture(null);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        SleepPatternResponse sleepPatternResponse;
        try {
            sleepPatternResponse = objectMapper.readValue(response.getChoices().get(0).getMessage().getContent(), SleepPatternResponse.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return CompletableFuture.completedFuture(sleepPatternResponse);
    }

    @Override
    public String getPrompt(String message) {
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
