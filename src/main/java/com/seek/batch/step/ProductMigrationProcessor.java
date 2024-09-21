package com.seek.batch.step;

import com.seek.batch.model.Product1;
import com.seek.batch.model.Product2;
import jakarta.annotation.Nonnull;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.batch.item.ItemProcessor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductMigrationProcessor implements ItemProcessor<Product1, Product2> {

    public static ProductMigrationProcessor getInstance() {
        return new ProductMigrationProcessor();
    }

    @Override
    public Product2 process(@Nonnull Product1 product1) {
        return Product2.from(product1);
    }
}