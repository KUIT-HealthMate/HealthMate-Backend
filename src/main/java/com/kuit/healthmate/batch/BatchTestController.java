package com.kuit.healthmate.batch;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class BatchTestController {

    private final JobLauncher jobLauncher;
    private final Job jobMonth;

    public BatchTestController(JobLauncher jobLauncher, @Qualifier("jobWeek") Job jobMonth) {
        this.jobLauncher = jobLauncher;
        this.jobMonth = jobMonth;
    }

    @SneakyThrows
    @GetMapping("/testBatch")
    public String test() {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("run.id", System.currentTimeMillis()) // 여기서 JobParameters에 파라미터 추가
                .toJobParameters();
        jobLauncher.run(jobMonth, jobParameters);
        return "Job executed successfully";
    }
}

