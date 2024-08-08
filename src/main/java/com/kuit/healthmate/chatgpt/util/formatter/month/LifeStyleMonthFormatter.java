package com.kuit.healthmate.chatgpt.util.formatter.month;

import com.kuit.healthmate.diagnosis.dto.PostDiagnosisRequest;
import com.kuit.healthmate.diagnosis.life.domain.LifeStyleQuestionnaire;
import com.kuit.healthmate.diagnosis.symtom.domain.SymptomInfo;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Slf4j
public class LifeStyleMonthFormatter {
    public Map<String, String> formatResponse(List<LifeStyleQuestionnaire> life){
        Map<String, List<LifeStyleQuestionnaire>> userToDataMap = new HashMap<>();

        for (LifeStyleQuestionnaire questionnaire : life) {
            String userName = questionnaire.getUser_name();
            userToDataMap
                    .computeIfAbsent(userName, k -> new ArrayList<>())
                    .add(questionnaire);
        }

        Map<String, String> formattedResponses = new HashMap<>();
        for (Map.Entry<String, List<LifeStyleQuestionnaire>> entry : userToDataMap.entrySet()) {
            String userName = entry.getKey();
            List<LifeStyleQuestionnaire> userData = entry.getValue();

            String response = formatResponse(userData,userName);
            formattedResponses.put(userName,response);
        }
        return formattedResponses;
    }
    private String formatResponse(List<LifeStyleQuestionnaire> dto, String userName) {
        String patientName = userName;
        StringBuilder response = new StringBuilder();
        response.append("너는 의사이고 나는 건강 진단을 받는 환자 ").append(patientName).append("이야. 아래의 질문에 대한 답변을 통해 나의 증상과 종합 평가를 내려줘\n\n");

        response.append("Q. 오늘의 근무/공부 환경 및 시간은?\n");
        response.append("1. 규칙적이다\n");
        response.append("2. 불규칙적이다\n\n");

        response.append("Q. 오늘 하루 쉬는 시간 없이 근무/공부에 집중한 시간을 알려주세요.\n");
        response.append("1. 1시간 이하\n");
        response.append("2. 2~3시간\n");
        response.append("3. 4~5시간\n");
        response.append("4. 6~7시간\n");
        response.append("5. 8시간 이상\n\n");

        response.append("Q. 오늘 하루 커피를 몇 잔 마셨나요?\n");
        response.append("1. 0잔\n");
        response.append("2. 1잔\n");
        response.append("3. 2잔\n");
        response.append("4. 3잔 이상\n\n");

        response.append("Q. 오늘 운동을 몇 시간 하셨나요?\n");
        response.append("1. 0시간\n");
        response.append("2. 1~2시간\n");
        response.append("3. 3시간 이상\n\n");

        response.append("Q. 오늘 하루 동안 업무/공부 도중 허리나 자세에 있어 불편함을 느낀 적이 있나요?\n");
        response.append("1. 있다\n");
        response.append("2. 없다\n\n");

        response.append("위의 문항에서 각각 ");
        for (LifeStyleQuestionnaire lifeStyleQuestionnaire : dto) {
            response.append("{");
            response.append(lifeStyleQuestionnaire.getEnvironmentScore()).append(",");
            response.append(lifeStyleQuestionnaire.getFocusTimeScore()).append(",");
            response.append(lifeStyleQuestionnaire.getCoffeeConsumptionScore()).append(",");
            response.append(lifeStyleQuestionnaire.getExerciseTimeScore()).append(",");
            response.append(lifeStyleQuestionnaire.getPostureDiscomfortScore()).append(",");
            response.append("}, \n");
        }
//        response.append("오늘 느껴진 이상 증세 - ");
//        if (symptomInfos.isEmpty()) {
//            response.append("없음\n");
//        } else {
//            for (SymptomInfo symptomInfo : symptomInfos) {
//                response.append(symptomInfo.getSymptomName()).append(", ");
//            }
//            response.append("\n");
//        }

        response.append("내가 한달동안 각각의 문항으로 부터 얻은 점수와 이상증세야\n" +
                "이를 바탕으로\n" +
                "예시와 같은 형식으로 분석을 해줘. 그리고 챌린지는 명사로 제시해줘. 만약 이상 증세가 없다면 위의 다른 값들을 가지고 종합 평가해. 형식을 맞춰줘.\n\n");

        response.append("여기부터는 예시이므로 아래의 형식을 참고\n\n");
        response.append("[진단 내용]\n\n");
        response.append("쿠잇 님의 한 달간의 생활 습관을 분석한 결과,\n 하지정맥류가 걱정돼요.\n\n하지정맥류가 있으면 무거운 느낌이 나고\n다리가 쉽게 피곤해지는 것 같고\n때로는 아리거나 아픈 느낌이 들기도 해요.\n오래 서 있거나 의자에 앉아 있으면 증상이 더\n심해질 수 있고, 특히 새벽녘에 종아리가 저리거나\n쥐남으로 잠을 깰 수도 있어요.\n\n겉으로 보면 피부에 거미줄 모양의 가는 실핏줄처럼 나타나기도 하고, 병이 좀 더 진행되면 늘어난\n정맥이 피부 밖으로 돌출되어 보이고\n만지면 부드럽지만 어떤 곳은 아픈 부위도 있어요.\n정확한 진단을 위해 근처의 혈관외과 방문을\n추천드려요.").append("\n\n");
        response.append("[생활 습관의 규칙성](0~100)\n").append("20\n\n");
        response.append("[일에 대한 몰입도](0~100)\n").append("80\n\n");
        response.append("[자세의 바른 정도](0~100)\n").append("60\n\n");
        response.append("[위험 증세 수치]\n").append("80\n\n");
        response.append("[위험 증세]\n").append("하지정맥류");
        response.append("[추천 챌린지]\n").append("걷기, 달리기, 스트레칭\n").append("\n\n");
        return response.toString();
    }
}