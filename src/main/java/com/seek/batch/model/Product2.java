package com.seek.batch.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product2 {

    @Id
    private Long id;
    private String productCode;
    private String productName;

    public static Product2 from(Product1 product1) {
        return Product2.builder()
            .id(product1.getId())
            .productCode(product1.getProductCode())
            .productName(product1.getProductName())
            .build();
    }
}
