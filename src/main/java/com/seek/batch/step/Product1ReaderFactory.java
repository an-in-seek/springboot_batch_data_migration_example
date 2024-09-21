package com.seek.batch.step;

import com.seek.batch.job.ProductMigrationJobConfig;
import com.seek.batch.model.Product1;
import jakarta.persistence.EntityManagerFactory;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Product1ReaderFactory {

    public static JpaPagingItemReader<Product1> createReader(EntityManagerFactory entityManagerFactory) {
        return new JpaPagingItemReaderBuilder<Product1>()
            .name("product1Reader")
            .entityManagerFactory(entityManagerFactory)
            .pageSize(ProductMigrationJobConfig.CHUNK_SIZE)
            .queryString("""
                SELECT p
                FROM Product1 p
                ORDER BY p.id DESC
                """)
            .build();
    }
}
