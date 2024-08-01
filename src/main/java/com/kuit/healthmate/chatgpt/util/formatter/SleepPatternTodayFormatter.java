package com.kuit.healthmate.chatgpt.util.formatter;

import com.kuit.healthmate.diagnosis.dto.PostDiagnosisRequest;
import com.kuit.healthmate.diagnosis.symtom.domain.SymptomInfo;

import java.util.List;

public class SleepPatternTodayFormatter {
    public String formatResponse(PostDiagnosisRequest dto){
        String patientName = dto.getUserName();
        int sleepDurationScore = dto.getSleepPatternDto().getSleepDurationScore();
        int morningFatigueScore = dto.getSleepPatternDto().getMorningFatigueScore();
        int peakConditionTimeScore = dto.getSleepPatternDto().getPeakConditionTimeScore();
        int sleepRemarkScore = dto.getSleepPatternDto().getSleepRemarkScore();
        List<SymptomInfo> symptomInfos = dto.getSymptomInfos();
        StringBuilder response = new StringBuilder();

        response.append("너는 의사이고 나는 건강 진단을 받는 환자 ").append(patientName).append("이야. 아래의 질문에 대한 답변을 통해 나의 증상과 종합 평가를 내려줘\n\n");

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


        response.append("위의 문항에서 각각 ").append(sleepDurationScore).append(",").append(morningFatigueScore).append(",")
                .append(peakConditionTimeScore).append(",").append(sleepRemarkScore).append("의 문항 번호를 선택했어\n");
        response.append("오늘 느껴진 이상 증세 - ");
        if (symptomInfos.isEmpty()) {
            response.append("없음\n");
        } else {
            for (SymptomInfo symptomInfo : symptomInfos) {
                response.append(symptomInfo.getSymptomName()).append(", ");
            }
            response.append("\n");
        }

        response.append(" 위의 평가와 이상 증세를 가지고 자세한 의심 질환과 분석 내용을 예시와 같은 형식으로 위의 내용을 분석을 해줘. 아래의 내용은 예시야. 만약 이상 증세가 없다면 위의 다른 값들을 가지고 종합 평가해. 형식을 맞춰줘.\n\n");

        response.append("여기부터는 예시이므로 아래의 형식을 참고\n\n");
        response.append("[진단 내용]\n\n");
        response.append("쿠잇 님의 하루 생활 습관을 분석한 결과,\n 하지정맥류가 걱정돼요.\n\n하지정맥류가 있으면 무거운 느낌이 나고\n다리가 쉽게 피곤해지는 것 같고\n때로는 아리거나 아픈 느낌이 들기도 해요.\n오래 서 있거나 의자에 앉아 있으면 증상이 더\n심해질 수 있고, 특히 새벽녘에 종아리가 저리거나\n쥐남으로 잠을 깰 수도 있어요.\n\n겉으로 보면 피부에 거미줄 모양의 가는 실핏줄처럼 나타나기도 하고, 병이 좀 더 진행되면 늘어난\n정맥이 피부 밖으로 돌출되어 보이고\n만지면 부드럽지만 어떤 곳은 아픈 부위도 있어요.\n정확한 진단을 위해 근처의 혈관외과 방문을\n추천드려요.").append("\n\n");
        response.append("[생활 습관의 규칙성](0~100)\n").append("20\n\n");
        response.append("[일에 대한 몰입도](0~100)\n").append("80\n\n");
        response.append("[자세의 바른 정도](0~100)\n").append("60\n\n");
        response.append("[위험 증세 수치]\n").append("80\n\n");
        response.append("[위험 증세]\n").append("하지정맥류");
        response.append("[추천 챌린지]\n)").append("걷기, 달리기, 스트레칭\n").append("\n\n");

        return response.toString();
    }
}
