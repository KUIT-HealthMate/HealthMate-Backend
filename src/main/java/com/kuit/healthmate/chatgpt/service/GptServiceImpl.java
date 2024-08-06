package com.kuit.healthmate.chatgpt.service;

import com.kuit.healthmate.chatgpt.dto.request.ChatRequest;
import com.kuit.healthmate.chatgpt.dto.response.ChatResponse;
import com.kuit.healthmate.chatgpt.dto.response.LifeStyleResponse;
import com.kuit.healthmate.chatgpt.dto.response.MealPatternResponse;
import com.kuit.healthmate.chatgpt.dto.response.SleepPatternResponse;
import com.kuit.healthmate.chatgpt.util.formatter.LifeStyleTodayFormatter;
import com.kuit.healthmate.chatgpt.util.formatter.MealPatternTodayFormatter;
import com.kuit.healthmate.chatgpt.util.formatter.SleepPatternTodayFormatter;
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
    public LifeStyleResponse getPromptByLifeStyle(PostDiagnosisRequest requestDto) {
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
    public MealPatternResponse getPromptByMeal(PostDiagnosisRequest requestDto) {
        MealPatternTodayFormatter mealPatternTodayFormatter = new MealPatternTodayFormatter();
        MealPatternTodayParser mealPatternTodayParser = new MealPatternTodayParser();

        String message = mealPatternTodayFormatter.formatResponse(requestDto);

        log.info(message);

        ChatRequest request = new ChatRequest(model, message);

        ChatResponse response = restTemplate.postForObject(apiUrl, request, ChatResponse.class);

        log.info(response.getChoices().get(0).getMessage().getContent());

        if (response == null || response.getChoices() == null || response.getChoices().isEmpty()) {
            return null;
        }

        return mealPatternTodayParser.parse(response.getChoices().get(0).getMessage().getContent());

    }

    @Override
    public SleepPatternResponse getPromptBySleep(PostDiagnosisRequest requestDto) {
        SleepPatternTodayFormatter sleepPatternTodayFormatter = new SleepPatternTodayFormatter();
        SleepPatternParser sleepPatternParser = new SleepPatternParser();

        String message = sleepPatternTodayFormatter.formatResponse(requestDto);

        log.info(message);

        ChatRequest request = new ChatRequest(model, message);

        ChatResponse response = restTemplate.postForObject(apiUrl, request, ChatResponse.class);

        log.info(response.getChoices().get(0).getMessage().getContent());

        if (response == null || response.getChoices() == null || response.getChoices().isEmpty()) {
            return null;
        }

        return sleepPatternParser.parse(response.getChoices().get(0).getMessage().getContent());
    }

    @Override
    public String getPrompt(String message) {
        ChatRequest request = new ChatRequest(model, message);

        ChatResponse response = restTemplate.postForObject(apiUrl, request, ChatResponse.class);

        log.info(response.getChoices().get(0).getMessage().getContent());

        if (response == null || response.getChoices() == null || response.getChoices().isEmpty()) {
            return null;
        }

        return response.getChoices().get(0).getMessage().getContent();
    }
}
