package com.kuit.healthmate.batch;


import com.kuit.healthmate.batch.task.FetchHealthDataTasklet;
import com.kuit.healthmate.batch.task.SendToGptAndSaveTasklet;
import com.kuit.healthmate.batch.task.TransformPromptTasklet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;


@Slf4j
@Configuration
@RequiredArgsConstructor
public class BatchConfig {
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final FetchHealthDataTasklet fetchHealthDataTasklet;
    private final TransformPromptTasklet transformPromptTasklet;
    private final SendToGptAndSaveTasklet sendToGptAndSaveTasklet;


    @Bean
    @JobScope
    public Job job() {
        return new JobBuilder("health", jobRepository)
                .start(fetchHealthDataStep())
                .next(transformPromptStep())
                .next(sendToGptStep())
                .build();
    }

    @Bean
    public Step fetchHealthDataStep() {
        return new StepBuilder("fetchHealthDataStep", jobRepository)
                .tasklet(fetchHealthDataTasklet, transactionManager)
                .build();
    }

    @Bean
    public Step transformPromptStep() {
        return new StepBuilder("transformPromptStep", jobRepository)
                .tasklet(transformPromptTasklet, transactionManager)
                .build();
    }

    @Bean
    public Step sendToGptStep() {
        return new StepBuilder("sendToGptStep", jobRepository)
                .tasklet(sendToGptAndSaveTasklet, transactionManager)
                .build();
    }

}
