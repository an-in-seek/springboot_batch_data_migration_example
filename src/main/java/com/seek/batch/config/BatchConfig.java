package com.seek.batch.config;

import com.seek.batch.model.Product1;
import com.seek.batch.repository.Product1JpaRepository;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BatchConfig {

    @Bean
    public static BeanDefinitionRegistryPostProcessor jobRegistryBeanPostProcessorRemover() {
        return registry -> registry.removeBeanDefinition("jobRegistryBeanPostProcessor");
    }

    @Bean
    public CommandLineRunner initData(Product1JpaRepository product1JpaRepository) {
        return args -> {
            product1JpaRepository.save(new Product1(1L, "P001", "Product Name 1"));
            product1JpaRepository.save(new Product1(2L, "P002", "Product Name 2"));
            product1JpaRepository.save(new Product1(3L, "P003", "Product Name 3"));
            product1JpaRepository.save(new Product1(4L, "P004", "Product Name 4"));
            product1JpaRepository.save(new Product1(5L, "P005", "Product Name 5"));
            product1JpaRepository.save(new Product1(6L, "P006", "Product Name 6"));
            product1JpaRepository.save(new Product1(7L, "P007", "Product Name 7"));
            product1JpaRepository.save(new Product1(8L, "P008", "Product Name 8"));
            product1JpaRepository.save(new Product1(9L, "P009", "Product Name 9"));
            product1JpaRepository.save(new Product1(10L, "P010", "Product Name 10"));

            product1JpaRepository.save(new Product1(11L, "P011", "Product Name 11"));
            product1JpaRepository.save(new Product1(12L, "P012", "Product Name 12"));
            product1JpaRepository.save(new Product1(13L, "P013", "Product Name 13"));
            product1JpaRepository.save(new Product1(14L, "P014", "Product Name 14"));
            product1JpaRepository.save(new Product1(15L, "P015", "Product Name 15"));
            product1JpaRepository.save(new Product1(16L, "P016", "Product Name 16"));
            product1JpaRepository.save(new Product1(17L, "P017", "Product Name 17"));
            product1JpaRepository.save(new Product1(18L, "P018", "Product Name 18"));
            product1JpaRepository.save(new Product1(19L, "P019", "Product Name 19"));
            product1JpaRepository.save(new Product1(20L, "P020", "Product Name 20"));
        };
    }
}