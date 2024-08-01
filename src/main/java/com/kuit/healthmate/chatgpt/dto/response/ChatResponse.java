package com.kuit.healthmate.chatgpt.dto.response;

import com.kuit.healthmate.chatgpt.dto.request.Message;
import lombok.Getter;

import java.util.List;

@Getter
public class ChatResponse {

    private List<Choice> choices;

    public static class Choice {

        private int index;
        private Message message;

        public int getIndex() {
            return index;
        }

        public Message getMessage() {
            return message;
        }
    }
}