package com.seek.batch;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.seek.batch.repository.Product1JpaRepository;
import com.seek.batch.repository.Product2JpaRepository;
import com.seek.batch.util.CommonUtil;
import java.time.LocalDateTime;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@SpringBatchTest
class SpringbootBatchExampleApplicationTests {

    @Autowired
    protected JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    protected JobRepositoryTestUtils jobRepositoryTestUtils;

    @Autowired
    private Product1JpaRepository product1JpaRepository;

    @Autowired
    private Product2JpaRepository product2JpaRepository;

    @BeforeEach
    protected void setUp(@Autowired Job productMigrationJob) {
        jobLauncherTestUtils.setJob(productMigrationJob);
        product1JpaRepository.saveAllAndFlush(CommonUtil.createProduct1List());
    }

    @AfterEach
    protected void cleanUp() {
        product2JpaRepository.deleteAllInBatch();
        product1JpaRepository.deleteAllInBatch();
        jobRepositoryTestUtils.removeJobExecutions();
    }

    @Test
    @DisplayName("productMigrationJob 테스트")
    void productMigrationJobTest() throws Exception {
        // given
        final JobParameters jobParameters = jobLauncherTestUtils.getUniqueJobParametersBuilder()
            .addLocalDateTime("dateTime", LocalDateTime.now())
            .toJobParameters();

        // when
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);

        // then
        assertEquals("productMigrationJob", jobExecution.getJobInstance().getJobName());
        assertEquals(BatchStatus.COMPLETED, jobExecution.getStatus());
        assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
        assertEquals(product1JpaRepository.count(), product2JpaRepository.count());
    }

}
