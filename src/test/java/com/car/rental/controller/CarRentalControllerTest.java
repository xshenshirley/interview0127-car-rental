package com.car.rental.controller;

import com.car.rental.constant.CarRentalConstants;
import com.car.rental.data.StockTracker;
import com.car.rental.exception.RentalCarLimitExceededException;
import com.car.rental.model.CarRentalRequest;
import com.car.rental.model.CarStockDetail;
import com.car.rental.model.CarStockDetailsResponse;
import com.car.rental.service.CarBookingServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

@RunWith(MockitoJUnitRunner.class)
public class CarRentalControllerTest {

    @Mock
    CarBookingServiceImpl carBookingService;

    @InjectMocks
    private CarRentalController carRentalController;

    @Test
    public void testGetAllCarsInStock(){
        CarStockDetail carStockDetail = new CarStockDetail("Test", 1);
        Mockito.when(carBookingService.getAllInStockCars()).thenReturn(Arrays.asList(carStockDetail));
        CarStockDetailsResponse expectedResponse = carRentalController.getAllInStockCars();
        Assert.assertEquals(expectedResponse.getCarStockDetails().get(0).getCarModel(), "Test");
    }

    @Test
    public void testRentCarsInStock() throws RentalCarLimitExceededException {
        CarStockDetail carStockDetail = new CarStockDetail("Test", 1);
        Mockito.when(carBookingService.rentCarsInStock("Test", 1, CarRentalConstants.StockAdjustInstruction.RENT))
                .thenReturn(Arrays.asList(carStockDetail));
        CarRentalRequest carRentalRequest = new CarRentalRequest();
        carRentalRequest.setCarModel("Test");
        carRentalRequest.setInstruction(CarRentalConstants.StockAdjustInstruction.RENT);
        carRentalRequest.setQuantity(1);
        CarStockDetailsResponse expectedResponse = carRentalController.rentCarsInStock(carRentalRequest);
        Assert.assertEquals(expectedResponse.getCarStockDetails().get(0).getStockSize(), 1);
    }

    @Test
    public void testReturnCarsToStock() throws RentalCarLimitExceededException {
        CarStockDetail carStockDetail = new CarStockDetail("Test", 2);
        Mockito.when(carBookingService.rentCarsInStock("Test", 1, CarRentalConstants.StockAdjustInstruction.RETURN))
                .thenReturn(Arrays.asList(carStockDetail));
        CarRentalRequest carRentalRequest = new CarRentalRequest();
        carRentalRequest.setCarModel("Test");
        carRentalRequest.setInstruction(CarRentalConstants.StockAdjustInstruction.RETURN);
        carRentalRequest.setQuantity(1);
        CarStockDetailsResponse expectedResponse = carRentalController.rentCarsInStock(carRentalRequest);
        Assert.assertEquals(expectedResponse.getCarStockDetails().get(0).getStockSize(), 2);
    }
}
