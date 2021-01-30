package com.car.rental.service;

import com.car.rental.constant.CarRentalConstants;
import com.car.rental.data.StockTracker;
import com.car.rental.exception.RentalCarLimitExceededException;
import com.car.rental.model.CarStockDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CarBookingServiceImpl implements CarBookingService{

    @Autowired
    private StockTracker stockTracker;

    @Override
    public List<CarStockDetail> getAllInStockCars() {
        return stockTracker.getAllCurrentStockDetails().values().stream().collect(Collectors.toList());
    }

    @Override
    public List<CarStockDetail> rentCarsInStock(String carModel, int quantity, CarRentalConstants.StockAdjustInstruction action) throws RentalCarLimitExceededException {
        stockTracker.modifyStockDetails(carModel, quantity, action);

        return stockTracker.getAllCurrentStockDetails().values().stream().collect(Collectors.toList());
    }

}
