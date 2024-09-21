package com.seek.batch.config;

import com.seek.batch.repository.Product1JpaRepository;
import com.seek.batch.util.CommonUtil;
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
        return args -> product1JpaRepository.saveAllAndFlush(CommonUtil.createProduct1List());
    }
}