package com.kuit.healthmate.batch;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BatchTestController {

    private final JobLauncher jobLauncher;
    private final Job jobMonth;

    public BatchTestController(JobLauncher jobLauncher, @Qualifier("jobMonth") Job jobMonth) {
        this.jobLauncher = jobLauncher;
        this.jobMonth = jobMonth;
    }

    @SneakyThrows
    @GetMapping("/testBatch")
    public String test() {
        jobLauncher.run(jobMonth, new JobParameters());
        return "Job executed successfully";
    }
}

