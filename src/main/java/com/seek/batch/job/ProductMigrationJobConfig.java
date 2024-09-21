package com.seek.batch.job;

import com.seek.batch.model.Product1;
import com.seek.batch.model.Product2;
import com.seek.batch.step.Product1ReaderFactory;
import com.seek.batch.step.Product2WriterFactory;
import com.seek.batch.step.ProductMigrationProcessor;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class ProductMigrationJobConfig {

    public static final int CHUNK_SIZE = 10;

    @Bean
    public Job productMigrationJob(JobRepository jobRepository, Step productMigrationStep) {
        return new JobBuilder("productMigrationJob", jobRepository)
            .incrementer(new RunIdIncrementer())
            .start(productMigrationStep)
            .build();
    }

    @Bean
    public Step productMigrationStep(
        JobRepository jobRepository,
        PlatformTransactionManager transactionManager,
        JpaPagingItemReader<Product1> reader,
        ItemProcessor<Product1, Product2> processor,
        ItemWriter<Product2> writer
    ) {
        return new StepBuilder("productMigrationStep", jobRepository)
            .<Product1, Product2>chunk(CHUNK_SIZE, transactionManager)
            .reader(reader)
            .processor(processor)
            .writer(writer)
            .build();
    }

    @Bean
    public JpaPagingItemReader<Product1> reader(EntityManagerFactory entityManagerFactory) {
        return Product1ReaderFactory.createReader(entityManagerFactory);
    }

    @Bean
    public ItemProcessor<Product1, Product2> processor() {
        return ProductMigrationProcessor.getInstance();
    }

    @Bean
    public JpaItemWriter<Product2> writer(EntityManagerFactory entityManagerFactory) {
        return Product2WriterFactory.createWriter(entityManagerFactory);
    }
}
