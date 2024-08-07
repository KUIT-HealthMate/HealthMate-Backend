package com.kuit.healthmate.chatgpt.util.formatter.month;

import com.kuit.healthmate.diagnosis.sleep.domain.SleepPatternQuestionnaire;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SleepPatternMonthFormatter {
    public Map<String, String> formatResponse(List<SleepPatternQuestionnaire> sleep){
        Map<String, List<SleepPatternQuestionnaire>> userToDataMap = new HashMap<>();

        for (SleepPatternQuestionnaire questionnaire : sleep) {
            String userName = questionnaire.getUser_name();
            userToDataMap
                    .computeIfAbsent(userName, k -> new ArrayList<>())
                    .add(questionnaire);
        }

        Map<String, String> formattedResponses = new HashMap<>();
        for (Map.Entry<String, List<SleepPatternQuestionnaire>> entry : userToDataMap.entrySet()) {
            String userName = entry.getKey();
            List<SleepPatternQuestionnaire> userData = entry.getValue();

            String response = formatResponse(userData,userName);
            formattedResponses.put(userName,response);
        }
        return formattedResponses;
    }


    public String formatResponse(List<SleepPatternQuestionnaire> sleep, String userName) {
        StringBuilder response = new StringBuilder();

        response.append("너는 의사이고 나는 건강 진단을 받는 환자 ").append(userName).append("이야. 아래의 질문에 대한 답변을 통해 나의 증상과 종합 평가를 내려줘\n\n");

        response.append("Q. 오늘 몇 시간 주무셨나요?\n");
        response.append("1. 1시간 이하\n");
        response.append("2. 2~3시간\n");
        response.append("3. 4~5시간\n");
        response.append("4. 6~7시간\n");
        response.append("5. 8시간 이\n\n");

        response.append("Q. 오늘 하루 아침에 느껴진 피로도 정도를 체크해주세요.\n");
        response.append("1. 매우 피곤함\n");
        response.append("2. 조금 피곤함\n");
        response.append("3. 조금 상쾌함\n");
        response.append("4. 매우 상쾌함\n\n");

        response.append("Q. 오늘 하루 컨디션이 가장 최고인 시간은 언제였나요?\n");
        response.append("1. 오전 5시 ~ 8시\n");
        response.append("2. 오전 8시 ~ 10시\n");
        response.append("3. 오전 10시 ~ 오후 5시\n");
        response.append("3. 오후 5시 ~ 오후 10시\n");
        response.append("4. 오후 10시 ~ 오전 5시\n\n");

        response.append("Q. 오늘 수면 중 특이사항은 없었나요?\n");
        response.append("1. 꿈\n");
        response.append("2. 뒤척임\n");
        response.append("3. 몸살(오한)\n");
        response.append("4. 불면증\n");
        response.append("5. 없음\n\n");


        for (SleepPatternQuestionnaire sleepPatternQuestionnaire : sleep) {
            response.append("{");
            response.append(sleepPatternQuestionnaire.getSleepDurationScore()).append(",");
            response.append(sleepPatternQuestionnaire.getMorningFatigueScore()).append(",");
            response.append(sleepPatternQuestionnaire.getPeakConditionTimeScore()).append(",");
            response.append(sleepPatternQuestionnaire.getSleepRemarkScore()).append(",");
            response.append("}, \n");
        }


        response.append(" 내가 한 달 동안 각각의 문항으로 부터 얻은 점수와 이상증세야\n" +
                "이를 바탕으로\n" +
                "예시와 같은 형식으로 분석을 해줘. 형식을 맞춰줘.\n\n");

        response.append("여기부터는 예시이므로 아래의 형식을 참고\n\n");
        response.append("[진단 내용]\n\n");
        response.append("쿠잇 님의 주간 생활 습관을 분석한 결과,\n 수면 패턴은 안정적이에요.\n\n다만, 간헐적으로 수면 패턴이 \n" +
                "불규칙한 경우가 있어,\n" +
                "수면 시간 조절에 신경을 써야 해요.지속적인 수면 패턴의 불규칙은 불면증을 초래할 수 있어요. 일반인의 약 1/3이 반복되는 불면증을 \n" +
                "경험하고, 9%가 매일의 일상생활에서 \n" +
                "불면증 때문에 괴로움을 느낀다고 해요.\n" +
                " \n" +
                "1개월 미만으로 지속되는 불면증은 대부분 \n" +
                "스트레스에 의하여 발생하므로 \n" +
                "스트레스를 제거할 수 있다면 \n" +
                "자연적으로 증상이 좋아지는 경우가 많아요. \n" +
                "만약 불면증의 증상이 나타난다면 근처의 신경과, \n" +
                "정신건강의학과 등에서 검사가 필요해요.").append("\n\n");
        response.append("[수면 패턴의 규칙성](0~100)\n").append("20\n\n");
        response.append("[수면의 질](0~100)\n").append("80\n\n");
        response.append("[수면의 집중도](0~100)\n").append("60\n\n");
        response.append("[위험 증세 수치]\n").append("80\n\n");
        response.append("[위험 증세]\n").append("없음\n\n");
        response.append("[추천 챌린지]\n").append("명상\n").append("\n\n");
        return response.toString();
    }
}
