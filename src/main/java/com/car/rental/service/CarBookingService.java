package com.car.rental.service;

import com.car.rental.constant.CarRentalConstants;
import com.car.rental.exception.RentalCarLimitExceededException;
import com.car.rental.model.CarStockDetail;

import java.util.List;

public interface CarBookingService {

    List<CarStockDetail> getAllInStockCars();
    List<CarStockDetail> rentCarsInStock(String carModel, int quantity, CarRentalConstants.StockAdjustInstruction action) throws RentalCarLimitExceededException;

}
