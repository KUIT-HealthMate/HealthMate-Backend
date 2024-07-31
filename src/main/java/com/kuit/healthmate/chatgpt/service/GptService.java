package com.kuit.healthmate.chatgpt.service;

import com.kuit.healthmate.chatgpt.dto.request.RequestDto;

public interface GptService {
    String getPrompt(RequestDto requestDto);
}
