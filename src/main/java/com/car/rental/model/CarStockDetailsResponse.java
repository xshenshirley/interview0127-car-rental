package com.car.rental.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by xinyishen on 2021/1/27.
 */
@Getter
@Setter
public class CarStockDetailsResponse {

    private String error;
    private boolean success;
    private List<CarStockDetail> carStockDetails;
}
