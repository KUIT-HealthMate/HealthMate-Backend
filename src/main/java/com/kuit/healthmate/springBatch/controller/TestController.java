package com.kuit.healthmate.springBatch.controller;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class TestController {
    private final Job testJob;
    private final JobLauncher jobLauncher;

    @SneakyThrows
    @GetMapping("/testBatch")
    public String test(){
        jobLauncher.run(testJob, new JobParameters());
        return null;
    }
}
