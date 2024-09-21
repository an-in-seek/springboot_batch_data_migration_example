package com.seek.batch.util;

import com.seek.batch.model.Product1;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommonUtil {

    public static List<Product1> createProduct1List() {
        List<Product1> productList = new ArrayList<>();
        IntStream.rangeClosed(1, 20).forEach(i ->
            productList.add(new Product1(String.format("P%03d", i), "Product Name " + i))
        );
        return productList;
    }
}
