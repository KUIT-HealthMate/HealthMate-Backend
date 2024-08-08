package com.kuit.healthmate.chatgpt.util.parser.month;

import com.kuit.healthmate.chatgpt.dto.response.LifeStyleResponse;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LifeStyleMonthParser {
    private static final Pattern DESCRIPTION_PATTERN =
            Pattern.compile("\\[진단 내용\\]\\s*\\n*([\\s\\S]*?)\\n*\\[생활 습관의 규칙성\\]", Pattern.DOTALL);

    private static final Pattern REGULARNESS_PATTERN =
            Pattern.compile("\\[생활 습관의 규칙성\\]\\(0~100\\)\\n(\\d+)");
    private static final Pattern IMMERSION_PATTERN =
            Pattern.compile("\\[일에 대한 몰입도\\]\\(0~100\\)\\n(\\d+)");
    private static final Pattern POSTURE_PATTERN =
            Pattern.compile("\\[자세의 바른 정도\\]\\(0~100\\)\\n(\\d+)");
    private static final Pattern RISK_SCORE_PATTERN =
            Pattern.compile("\\[위험 증세 수치\\]\\n(\\d+)");
    private static final Pattern RISK_SYMPTOMS_PATTERN =
            Pattern.compile("\\[위험 증세\\]\\n(.+)");
    private static final Pattern CHALLENGES_PATTERN =
            Pattern.compile("\\[추천 챌린지\\]\\n([^\n]*)");

    private static String extractGroup(Pattern pattern, String text) {
        Matcher matcher = pattern.matcher(text);
        return matcher.find() ? matcher.group(1).trim() : null;
    }

    private static Integer extractInteger(Pattern pattern, String text) {
        String result = extractGroup(pattern, text);
        return result != null ? Integer.parseInt(result) : null;
    }

    public LifeStyleResponse parse(String promptResponse) {
        LifeStyleResponse dto = new LifeStyleResponse();

        String description = extractGroup(DESCRIPTION_PATTERN, promptResponse);
        if (description != null) {
            description = description.replaceAll("\\n+", " ");
        }
        dto.setDescription(description);

        dto.setRegularness(extractInteger(REGULARNESS_PATTERN, promptResponse));
        dto.setImmersion(extractInteger(IMMERSION_PATTERN, promptResponse));
        dto.setPosture(extractInteger(POSTURE_PATTERN, promptResponse));
        dto.setRiskScore(extractInteger(RISK_SCORE_PATTERN, promptResponse));
        dto.setRiskSymptoms(extractGroup(RISK_SYMPTOMS_PATTERN, promptResponse));
        dto.setChallenges(extractGroup(CHALLENGES_PATTERN, promptResponse));

        return dto;
    }
}
