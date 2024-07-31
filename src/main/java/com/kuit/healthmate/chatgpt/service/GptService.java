package com.kuit.healthmate.chatgpt.service;

import com.kuit.healthmate.chatgpt.dto.RequestDto;

public interface GptService {
    String getPrompt(RequestDto requestDto);
}
