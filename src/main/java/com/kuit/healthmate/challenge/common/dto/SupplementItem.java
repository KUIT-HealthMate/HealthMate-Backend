package com.kuit.healthmate.challenge.common.dto;

import com.kuit.healthmate.challenge.supplement.domain.Supplement;
import com.kuit.healthmate.challenge.supplement.domain.SupplementChecker;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class SupplementItem {
    private String challengeName; // 챌린지명

    private Boolean breakfastSuccess; // 아침 달성 여부
    private Boolean lunchSuccess;    // 점심 달성 여부
    private Boolean dinnerSuccess; // 저녁 달성 여부

    private Boolean breakfastRequired;
    private Boolean lunchRequired;
    private Boolean dinnerRequired;

    public SupplementItem(String challengeName, Boolean breakfastSuccess, Boolean lunchSuccess, Boolean dinnerSuccess,
                          Boolean breakfastRequired, Boolean lunchRequired, Boolean dinnerRequired) {
        this.challengeName = challengeName;
        this.breakfastSuccess = breakfastSuccess;
        this.lunchSuccess = lunchSuccess;
        this.dinnerSuccess = dinnerSuccess;
        this.breakfastRequired = breakfastRequired;
        this.lunchRequired = lunchRequired;
        this.dinnerRequired = dinnerRequired;
    }

    public static SupplementItem fromSupplement(Supplement supplement, LocalDate date) {
        boolean breakfastRequired = supplement.getSupplementRoutine().getBreakfast();
        boolean lunchRequired = supplement.getSupplementRoutine().getLunch();
        boolean dinnerRequired = supplement.getSupplementRoutine().getDinner();

        boolean breakfastSuccess = supplement.getSupplementCheckers().stream()
                .filter(checker -> checker.getCheckDate().equals(date))
                .anyMatch(SupplementChecker::isBreakfastChecked);
        boolean lunchSuccess = supplement.getSupplementCheckers().stream()
                .filter(checker -> checker.getCheckDate().equals(date))
                .anyMatch(SupplementChecker::isLunchChecked);
        boolean dinnerSuccess = supplement.getSupplementCheckers().stream()
                .filter(checker -> checker.getCheckDate().equals(date))
                .anyMatch(SupplementChecker::isDinnerChecked);

        return new SupplementItem(
                supplement.getName(),
                breakfastRequired,
                lunchRequired,
                dinnerRequired,
                breakfastSuccess,
                lunchSuccess,
                dinnerSuccess
        );
    }

    public boolean getSuccess() {
        int totalRequired = 0;
        int totalSuccess = 0;

        if (breakfastRequired != null && breakfastRequired) {
            totalRequired++;
            if (breakfastSuccess != null && breakfastSuccess) {
                totalSuccess++;
            }
        }

        if (lunchRequired != null && lunchRequired) {
            totalRequired++;
            if (lunchSuccess != null && lunchSuccess) {
                totalSuccess++;
            }
        }

        if (dinnerRequired != null && dinnerRequired) {
            totalRequired++;
            if (dinnerSuccess != null && dinnerSuccess) {
                totalSuccess++;
            }
        }

        return totalSuccess == totalRequired;
    }
}