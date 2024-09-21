package com.seek.batch.step;

import com.seek.batch.model.Product2;
import jakarta.persistence.EntityManagerFactory;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Product2WriterFactory {

    public static JpaItemWriter<Product2> createWriter(EntityManagerFactory entityManagerFactory) {
        return new JpaItemWriterBuilder<Product2>()
            .usePersist(true)
            .entityManagerFactory(entityManagerFactory)
            .build();
    }

}
