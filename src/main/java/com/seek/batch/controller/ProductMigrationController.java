package com.seek.batch.controller;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/batches/product-migration")
public class ProductMigrationController {

    private final JobLauncher jobLauncher;
    private final Job productMigrationJob;

    @PostMapping
    public ResponseEntity<Void> productMigration() throws Throwable {
        final JobParameters jobParameters = new JobParametersBuilder()
            .addLocalDateTime("dateTime", LocalDateTime.now())
            .toJobParameters();
        final JobExecution jobExecution = jobLauncher.run(productMigrationJob, jobParameters);
        if (ExitStatus.FAILED.equals(jobExecution.getExitStatus())) {
            log.warn("JobExecution failed with exit status: {}", jobExecution.getExitStatus().getExitCode());
        }
        return ResponseEntity.noContent().build();
    }

}