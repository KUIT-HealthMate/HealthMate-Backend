package com.kuit.healthmate.chatgpt.util;

import com.kuit.healthmate.diagnosis.dto.PostDiagnosisRequest;
import com.kuit.healthmate.diagnosis.symtom.domain.SymptomInfo;

import java.util.List;

public class LifeStyleTodayFormatter {
    public String formatResponse(PostDiagnosisRequest dto) {
        String patientName = dto.getUserName();
        // DTO에서 각 항목의 값을 가져옵니다.
        Integer environmentScore = dto.getLifeStyleDto().getEnvironmentScore();
        Integer focusTimeScore = dto.getLifeStyleDto().getFocusTimeScore();
        Integer coffeeConsumptionScore = dto.getLifeStyleDto().getCoffeeConsumptionScore();
        Integer exerciseTimeScore = dto.getLifeStyleDto().getExerciseTimeScore();
        Integer postureDiscomfortScore = dto.getLifeStyleDto().getPostureDiscomfortScore();
        List<SymptomInfo> symptomInfos = dto.getSymptomInfos();

        // 응답 문자열을 생성합니다.
        StringBuilder response = new StringBuilder();

        response.append("너는 의사이고 나는 건강 진단을 받는 환자 ").append(patientName).append("이야. 아래의 질문에 대한 답변을 통해 나의 증상과 종합 평가를 내려줘\n\n");

        response.append("Q. 오늘의 근무/공부 환경 및 시간은?\n");
        response.append("규칙적이다 (2점)\n");
        response.append("불규칙적이다 (1점)\n\n");

        response.append("Q. 오늘 하루 쉬는 시간 없이 근무/공부에 집중한 시간을 알려주세요.\n");
        response.append("1시간 이하 (5점)\n");
        response.append("2~3시간 (4점)\n");
        response.append("4~5시간 (3점)\n");
        response.append("6~7시간 (2점)\n");
        response.append("8시간 이상 (1점)\n\n");

        response.append("Q. 오늘 하루 커피를 몇 잔 마셨나요?\n");
        response.append("0잔 (4점)\n");
        response.append("1잔 (3점)\n");
        response.append("2잔 (2점)\n");
        response.append("3잔 이상 (1점)\n\n");

        response.append("Q. 오늘 운동을 몇 시간 하셨나요?\n");
        response.append("0시간 (1점)\n");
        response.append("1~2시간 (2점)\n");
        response.append("3시간 이상 (3점)\n\n");

        response.append("Q. 오늘 하루 동안 업무/공부 도중 허리나 자세에 있어 불편함을 느낀 적이 있나요?\n");
        response.append("예 (2점)\n");
        response.append("아니요 (1점)\n\n");

        response.append("각각의 문항에서 내가 ").append(environmentScore).append(",").append(focusTimeScore).append(",").append(coffeeConsumptionScore).append(",").append(exerciseTimeScore).append(",").append(postureDiscomfortScore).append(" 점의 점수를 얻었고\n");
        response.append("오늘 느껴진 이상 증세 - ");
        for (SymptomInfo symptomInfo : symptomInfos) {
            response.append(symptomInfo.getSymptomName());
        }
        response.append(" 일 때 위의 평가와 이상 증세를 가지고 자세한 의심 질환과 분석 내용을 예시와 같은 형식으로 위의 내용을 분석을 해줘.\n\n");

        response.append("[진단 내용]\n\n");
        response.append(patientName).append(" 님의 하루 생활 습관을 분석한 결과,\n");
        response.append("쿠잇 님의 하루 생활 습관을 분석한 결과,\n 빈혈이 걱정돼요.\n\n하지정맥류가 있으면 무거운 느낌이 나고\n다리가 쉽게 피곤해지는 것 같고\n때로는 아리거나 아픈 느낌이 들기도 해요.\n오래 서 있거나 의자에 앉아 있으면 증상이 더\n심해질 수 있고, 특히 새벽녘에 종아리가 저리거나\n쥐남으로 잠을 깰 수도 있어요.\n\n겉으로 보면 피부에 거미줄 모양의 가는 실핏줄처럼 나타나기도 하고, 병이 좀 더 진행되면 늘어난\n정맥이 피부 밖으로 돌출되어 보이고\n만지면 부드럽지만 어떤 곳은 아픈 부위도 있어요.\n정확한 진단을 위해 근처의 혈관외과 방문을\n추천드려요.").append("\n\n");

        response.append("[생활 습관의 규칙성](0~100)\\n20\\n\\n[일에 대한 몰입도](0~100)\\n80\\n\\n[자세의 바른 정도](0~100)\\n60\\n\\n[위험 증세 수치]\\n80\\n\\n[위험 증세]\\n빈혈\\n\\n[추천 챌린지]\\n걷기, 달리기, 스트레칭\n");
        return response.toString();
    }
}
