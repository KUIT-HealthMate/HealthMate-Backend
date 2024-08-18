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

import java.util.Date;

@Component
@EnableScheduling
public class BatchScheduler {

    private final JobLauncher jobLauncher;
    private final Job jobWeek;
    private final Job jobMonth;
    private final Job jobAverage;

    @Autowired
    public BatchScheduler(JobLauncher jobLauncher, @Qualifier("jobWeek")Job jobWeek, @Qualifier("jobMonth") Job jobMonth,
                          @Qualifier("jobAverage") Job jobAverage) {
        this.jobLauncher = jobLauncher;
        this.jobWeek = jobWeek;
        this.jobMonth = jobMonth;
        this.jobAverage = jobAverage;
    }

    @Scheduled(cron = "0 0 23 * * SUN")
    public void runBatchJob() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("run.id", System.currentTimeMillis()) // 여기서 JobParameters에 파라미터 추가
                .addLong("time", new Date().getTime())
                .toJobParameters();

        jobLauncher.run(jobWeek, jobParameters); // Job 실행
    }
    @Scheduled(cron = "0 0 23 L * ?")
    public void runBatchJobMonth() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("run.id", System.currentTimeMillis()) // 여기서 JobParameters에 파라미터 추가
                .addLong("time", new Date().getTime())
                .toJobParameters();
        jobLauncher.run(jobMonth, jobParameters);
    }

//    @Scheduled(cron = "0 30 23 * * ?")
//    public void runBatchJobAverage() throws Exception {
//        jobLauncher.run(jobAverage, new JobParameters());
//    }
}