package com.kuit.healthmate.chatgpt.service;

import com.kuit.healthmate.chatgpt.dto.ChatRequest;
import com.kuit.healthmate.chatgpt.dto.ChatResponse;
import com.kuit.healthmate.chatgpt.dto.RequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

public class GptServiceImpl implements GptService{
    @Qualifier("openaiRestTemplate")
    @Autowired
    private RestTemplate restTemplate;

    @Value("${openai.model}")
    private String model;

    @Value("${openai.api.url}")
    private String apiUrl;
    @Override
    public String getPrompt(RequestDto prompt) {
        ChatRequest request = new ChatRequest(model, prompt.getMessage());

        ChatResponse response = restTemplate.postForObject(apiUrl, request, ChatResponse.class);

        if (response == null || response.getChoices() == null || response.getChoices().isEmpty()) {
            return "No response";
        }

        return response.getChoices().get(0).getMessage().getContent();
    }
}
