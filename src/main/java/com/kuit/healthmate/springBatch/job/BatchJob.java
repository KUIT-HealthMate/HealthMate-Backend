package com.kuit.healthmate.springBatch.job;


import com.kuit.healthmate.chatgpt.dto.response.LifeStyleResponse;
import com.kuit.healthmate.diagnosis.common.service.DiagnosisService;
import java.util.stream.Collectors;
import com.kuit.healthmate.diagnosis.life.domain.LifeStyleQuestionnaire;
import com.kuit.healthmate.diagnosis.meal.domain.MealPatternQuestionnaire;
import com.kuit.healthmate.diagnosis.sleep.domain.SleepPatternQuestionnaire;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.DuplicateJobException;
import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Configuration
public class BatchJob extends DefaultBatchConfiguration {
    private final DiagnosisService diagnosisService;
    private final EntityManagerFactory entityManagerFactory;
    @Bean
    public Job GPTBatchMonthJob(JobRepository jobRepository, PlatformTransactionManager transactionManager) throws DuplicateJobException {
        return new JobBuilder("GPTBatchMonthJob",jobRepository)
                .start(StepLifeStyleMonth(jobRepository,transactionManager))
//                .next(StepMealPatternMonth(jobRepository,transactionManager))
//                .next(StepSleepPatternMonth(jobRepository,transactionManager))
                .build();
    }

    @Bean
    public Step StepLifeStyleMonth(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("Step1",jobRepository)
                .<List<LifeStyleQuestionnaire>, List<LifeStyleResponse>>chunk(5,transactionManager)
                .reader(readerLifeStyle())
                .processor(processorLifeStyle())
                .writer(writerLifeStyle())
                .build();
    }



//    @Bean
//    public Step StepMealPatternMonth(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
//        return new StepBuilder("Step1",jobRepository)
//                .chunk(5,transactionManager)
//                .reader(readerMealPattern())
//                .processor(processorMealPattern())
//                .writer(writerMealPattern())
//                .build();
//    }
//    @Bean
//    public Step StepSleepPatternMonth(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
//        return new StepBuilder("Step1",jobRepository)
//                .chunk(5,transactionManager)
//                .reader(readerSleepPattern())
//                .processor(processorSleepPattern())
//                .writer(writerSleepPattern())
//                .build();
//    }
    @Bean
    public ItemReader<List<LifeStyleQuestionnaire>> readerLifeStyle() {
        LocalDate firstDayOfMonth = LocalDate.now().withDayOfMonth(1);
        LocalDate lastDayOfMonth = firstDayOfMonth.plusMonths(1).minusDays(1);
        return new JpaPagingItemReaderBuilder<List<LifeStyleQuestionnaire>>()
                .name("dailyLifeStyleReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("SELECT d FROM LifeStyleQuestionnaire d WHERE d.date >= :startDate AND d.date <= :endDate")
                .parameterValues(Map.of("startDate", firstDayOfMonth, "endDate", lastDayOfMonth))
                .pageSize(10)
                .build();
    }
    @Bean
    public ItemProcessor<List<LifeStyleQuestionnaire>, List<LifeStyleResponse>> processorLifeStyle() {
        return items -> {
            // 사용자별로 데이터를 그룹화하여 월간 결과 생성
            Map<Long, List<LifeStyleQuestionnaire>> groupedByUser = items.stream()
                    .collect(Collectors.groupingBy(LifeStyleQuestionnaire::getUserId));

            List<LifeStyleResponse> monthlyDiagnoses = new ArrayList<>();
            for (Map.Entry<Long, List<LifeStyleQuestionnaire>> entry : groupedByUser.entrySet()) {
                Long userId = entry.getKey();
                List<LifeStyleQuestionnaire> dailyDiagnoses = entry.getValue();
                LifeStyleResponse monthlyDiagnosis = diagnosisService.createMonthlyDiagnosis(userId, dailyDiagnoses); //gpt 호출
                monthlyDiagnoses.add(monthlyDiagnosis);
            }
            return monthlyDiagnoses;
        };
    }
    @Bean
    public ItemWriter<List<LifeStyleResponse>> writerLifeStyle() {
        return items -> {
            // 월간 결과를 DB에 저장
            for (List<LifeStyleResponse> item : items) {
               // diagnosisService.saveMonthlyDiagnosis(item);
            }
        };
    }
//    @Bean
//    public ItemReader<MealPatternQuestionnaire> readerMealPattern() {
//        LocalDate firstDayOfMonth = LocalDate.now().withDayOfMonth(1);
//        LocalDate lastDayOfMonth = firstDayOfMonth.plusMonths(1).minusDays(1);
//        return new JpaPagingItemReaderBuilder<MealPatternQuestionnaire>()
//                .name("dailyMealPatternReader")
//                .entityManagerFactory(entityManagerFactory)
//                .queryString("SELECT d FROM MealPatternQuestionnaire d WHERE d.date >= :startDate AND d.date <= :endDate")
//                .parameterValues(Map.of("startDate", firstDayOfMonth, "endDate", lastDayOfMonth))
//                .pageSize(10)
//                .build();
//    }
//    @Bean
//    public ItemReader<SleepPatternQuestionnaire> readerSleepPattern() {
//        LocalDate firstDayOfMonth = LocalDate.now().withDayOfMonth(1);
//        LocalDate lastDayOfMonth = firstDayOfMonth.plusMonths(1).minusDays(1);
//        return new JpaPagingItemReaderBuilder<SleepPatternQuestionnaire>()
//                .name("dailySleepPatternReader")
//                .entityManagerFactory(entityManagerFactory)
//                .queryString("SELECT d FROM SleepPatternQuestionnaire d WHERE d.date >= :startDate AND d.date <= :endDate")
//                .parameterValues(Map.of("startDate", firstDayOfMonth, "endDate", lastDayOfMonth))
//                .pageSize(10)
//                .build();
//    }


}
