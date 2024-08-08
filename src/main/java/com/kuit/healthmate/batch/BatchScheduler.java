package com.kuit.healthmate.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class BatchScheduler {

    private final JobLauncher jobLauncher;
    private final Job jobWeek;
    private final Job jobMonth;

    @Autowired
    public BatchScheduler(JobLauncher jobLauncher, @Qualifier("jobWeek")Job jobWeek, @Qualifier("jobMonth") Job jobMonth) {
        this.jobLauncher = jobLauncher;
        this.jobWeek = jobWeek;
        this.jobMonth = jobMonth;
    }

//    @Scheduled(fixedRate = 600000)
//    public void runBatchJob() throws Exception {
//        JobParameters jobParameters = new JobParametersBuilder()
//                .addLong("run.id", System.currentTimeMillis()) // 여기서 JobParameters에 파라미터 추가
//                .toJobParameters();
//
//        jobLauncher.run(jobWeek, jobParameters); // Job 실행
//    }
//    @Scheduled(fixedRate = 1200000) // 임시로 설정
//    public void runBatchJobMonth() throws Exception {
//        jobLauncher.run(jobMonth, new JobParameters());
//    }
}