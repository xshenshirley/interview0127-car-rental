package com.car.rental.data;

import com.car.rental.constant.CarRentalConstants;
import com.car.rental.exception.RentalCarLimitExceededException;
import com.car.rental.model.CarStockDetail;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

@RunWith(MockitoJUnitRunner.class)
public class StockTrackerTest {

    @Mock
    Map<String, Integer> initialStock;

    @InjectMocks
    StockTracker stockTracker;

    @Test
    public void testRentCarsFromStock() throws RentalCarLimitExceededException {
        stockTracker.modifyStockDetails("Toyota Camry", 1, CarRentalConstants.StockAdjustInstruction.RENT);
        Map<String, CarStockDetail> currentStock = stockTracker.getAllCurrentStockDetails();
        Assert.assertEquals(currentStock.get("Toyota Camry").getStockSize(), 1);
    }

    @Test
    public void testRentAllCarsForOneModel() throws RentalCarLimitExceededException {
        stockTracker.modifyStockDetails("Toyota Camry", 2, CarRentalConstants.StockAdjustInstruction.RENT);
        Map<String, CarStockDetail> currentStock = stockTracker.getAllCurrentStockDetails();
        Assert.assertEquals(currentStock.get("Toyota Camry").getStockSize(), 0);
    }

    @Test
    public void testRentAndReturnCarsToStock() throws RentalCarLimitExceededException {
        Mockito.when(initialStock.get(Mockito.any(String.class))).thenReturn(2);
        stockTracker.modifyStockDetails("Toyota Camry", 1, CarRentalConstants.StockAdjustInstruction.RENT);
        stockTracker.modifyStockDetails("Toyota Camry", 1, CarRentalConstants.StockAdjustInstruction.RETURN);
        Map<String, CarStockDetail> currentStock = stockTracker.getAllCurrentStockDetails();
        Assert.assertEquals(currentStock.get("Toyota Camry").getStockSize(), 2);
    }

    @Test
    public void testRentCarsNotEnoughToStock() {
        try {
            stockTracker.modifyStockDetails("Toyota Camry", 1, CarRentalConstants.StockAdjustInstruction.RENT);
            stockTracker.modifyStockDetails("Toyota Camry", 1, CarRentalConstants.StockAdjustInstruction.RENT);
            stockTracker.modifyStockDetails("Toyota Camry", 1, CarRentalConstants.StockAdjustInstruction.RENT);
        } catch (RentalCarLimitExceededException e) {
            Assert.assertEquals(e.getErrorMessage(), "Could not rent Car Model Toyota Camry with quantity 1, due to not enough cars for rent in store");
        }

        Map<String, CarStockDetail> currentStock = stockTracker.getAllCurrentStockDetails();
        Assert.assertEquals(currentStock.get("Toyota Camry").getStockSize(), 0);
    }

    @Test
    public void testReturnCarsExceedsCarLimit() {
        Mockito.when(initialStock.get(Mockito.any(String.class))).thenReturn(2);
        try {
            stockTracker.modifyStockDetails("Toyota Camry", 1, CarRentalConstants.StockAdjustInstruction.RETURN);
        } catch (RentalCarLimitExceededException e) {
            Assert.assertEquals(e.getErrorMessage(), "Could not rent Car Model Toyota Camry with quantity 1, due to invalid operation, car limit exceed");
        }

        Map<String, CarStockDetail> currentStock = stockTracker.getAllCurrentStockDetails();
        Assert.assertEquals(currentStock.get("Toyota Camry").getStockSize(), 2);
    }
}
