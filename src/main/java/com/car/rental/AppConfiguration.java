package com.car.rental;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

import static com.car.rental.constant.CarRentalConstants.BMW_650;
import static com.car.rental.constant.CarRentalConstants.TOYOTA_CAMRY;

/**
 * Created by xinyishen on 2021/1/26.
 */
@Configuration
public class AppConfiguration {

    @Bean
    public Map<String, Integer> initialStock() {
        final Map<String, Integer> inStockCarStockDetails =new HashMap<>();
        inStockCarStockDetails.put(TOYOTA_CAMRY, 2);
        inStockCarStockDetails.put(BMW_650, 2);

        return inStockCarStockDetails;
    }
}
