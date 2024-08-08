package com.kuit.healthmate.chatgpt.util.formatter.month;


import com.kuit.healthmate.diagnosis.meal.domain.MealPatternQuestionnaire;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Slf4j
public class MealPatternMonthFormatter {

    public Map<String, String> formatResponse(List<MealPatternQuestionnaire> meal){
        Map<String, List<MealPatternQuestionnaire>> userToDataMap = new HashMap<>();

        for (MealPatternQuestionnaire questionnaire : meal) {
            String userName = questionnaire.getUser_name();
            userToDataMap
                    .computeIfAbsent(userName, k -> new ArrayList<>())
                    .add(questionnaire);
        }

        Map<String, String> formattedResponses = new HashMap<>();
        for (Map.Entry<String, List<MealPatternQuestionnaire>> entry : userToDataMap.entrySet()) {
            String userName = entry.getKey();
            List<MealPatternQuestionnaire> userData = entry.getValue();

            String response = formatResponse(userData,userName);
            formattedResponses.put(userName,response);
        }
        return formattedResponses;
    }


    public String formatResponse(List<MealPatternQuestionnaire> meal, String userName){
        StringBuilder response = new StringBuilder();
        log.info(userName);
        response.append("너는 의사이고 나는 건강 진단을 받는 환자 ").append(userName).append("이야. 아래의 질문에 대한 답변을 통해 나의 증상과 종합 평가를 내려줘\n\n");

        response.append("Q. 오늘 언제 식사하셨나요?\n");
        response.append("1. 아침\n");
        response.append("2. 점심\n\n");
        response.append("2. 저녁\n\n");

        response.append("Q. 오늘 먹은 음식 종류를 선택해주세요.\n");
        response.append("1. 한식\n");
        response.append("2. 일식\n");
        response.append("3. 중식\n");
        response.append("4. 양식\n");
        response.append("5. 기타\n\n");

        response.append("Q. 오늘의 식사 시간은?\n");
        response.append("1. 규칙적이다.\n");
        response.append("2. 불규칙적이다.\n");

        response.append("Q.  한 끼 식사에 소요된 시간을 알려주세요. \n");
        response.append("1. 10분~20분 미만\n");
        response.append("2. 20분 이상~1시간 미만\n");
        response.append("3. 1시간 이상\n\n");

        response.append("Q. 식사를 할 때 소금이나 설탕 등 조미료를 많이 섭취했나요?\n");
        response.append("1. 예\n");
        response.append("2. 아니오\n\n");

        response.append("Q. 식사를 하는 데 TV나 스마트폰을 함께 봤나요?\n");
        response.append("1. 예\n");
        response.append("2. 아니오\n\n");

        response.append("Q. 오늘 하루 식사 도중 느낀 특이점이 있었나요?\n");
        response.append("1. 더부룩함\n");
        response.append("2. 복부 팽만\n");
        response.append("3. 입맛 없음\n");
        response.append("4. 폭식\n");
        response.append("5. 없음\n\n");

        for (MealPatternQuestionnaire mealPatternQuestionnaire : meal) {
            response.append("{");
            response.append(mealPatternQuestionnaire.getMealTimeScore()).append(",");
            response.append(mealPatternQuestionnaire.getFoodType()).append(",");
            response.append(mealPatternQuestionnaire.getRegularMealTimeScore()).append(",");
            response.append(mealPatternQuestionnaire.getMealDurationScore()).append(",");
            response.append(mealPatternQuestionnaire.getSeasoningConsumptionScore()).append(",");
            response.append(mealPatternQuestionnaire.getScreenUsage()).append(",");
            response.append(mealPatternQuestionnaire.getMealRemark()).append(",");
            response.append("}, \n");
        }


        response.append(" 내가 한 달 동안 각각의 문항으로 부터 얻은 점수와 이상증세야\n" +
                "이를 바탕으로\n" +
                "예시와 같은 형식으로 분석을 해줘. 형식을 맞춰줘.\n\n");

        response.append("[진단 내용]\n\n");
        response.append("쿠잇 님의  한 달간의 식사 습관을 분석한 결과,\n" +
                "식사 패턴이 대체로 규칙적이지만, \n" +
                "간헐적으로 과식 또는 불규칙한 식사를 하시는 것\n" +
                "으로 보여요.\n" +
                "\n" +
                "이러한 식사 습관은 소화 과정에 부담을 주고, \n" +
                "장기적으로 소화불량을 유발할 수 있어요. \n" +
                "일반적으로 많은 사람들이 가볍게 여기는 소화불량\n" +
                "은, 실제로 우리 몸의 건강 상태를 반영하는 \n" +
                "중요한 신호일 수 있어요. \n" +
                "약 20%의 사람들이 일상생활에서 소화불량으로\n" +
                "인한 불편함을 경험하고 있으며, \n" +
                "그 중 일부는 식사 습관의 부적절함으로 인해 \n" +
                "발생한다고 해요. \n" +
                "\n" +
                "단기간에 나타나는 소화불량은 대부분 식사 습관의\n" +
                "변화로 개선될 수 있으나, 만약 증상이 지속된다면\n" +
                "내과나 소화기내과에서 정확한 진단과 함께 상담이\n" +
                "필요해요.\n" +
                "\n" +
                "정기적인 식사 시간을 유지하고, 과식을 피하며, \n" +
                "균형 잡힌 식사를 하는 것이 중요해요. \n" +
                "또한, 식사 후 적절한 활동을 통해 소화를 돕는 것\n" +
                "도 좋은 방법이에요.").append("\n\n");
        response.append("[식사 패턴의 규칙성](0~100)\n").append("20\n\n");
        response.append("[음주 빈도](0~100)\n").append("80\n\n");
        response.append("[영양분 섭취 정도](0~100)\n").append("60\n\n");
        response.append("[위험 증세 수치]\n").append("80\n\n");
        response.append("[위험 증세]\n").append("없음\n\n");
        response.append("[추천 챌린지]\n").append("15번 이상 씹기\n").append("\n\n");

        return response.toString();
    }
}
