package com.konai.hsyang.konatoybe.batchWebApi.schedulers;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class TutorialScheduler {

    private final Job job;
    private final JobLauncher jobLauncher;

    //    @Scheduled(fixedDelay = 5 * 1000L) // @Scheduled 어노테이션을 이용하여 일정한 주기마다 작성한 Job이 실행되도록 설정
//    public void executeJob() {
//        try {
//            jobLauncher.run(
//                    job,
//                    new JobParametersBuilder()
//                            .addString("datetime", LocalDateTime.now().toString())
//                            .toJobParameters()
//            );
//        } catch (JobExecutionException e) {
//            System.out.println(e.getMessage());
//            e.printStackTrace();
//        }
//    }
}
