package com.car.rental.data;

import com.car.rental.constant.CarRentalConstants;
import com.car.rental.exception.RentalCarLimitExceededException;
import com.car.rental.model.CarStockDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.car.rental.constant.CarRentalConstants.BMW_650;
import static com.car.rental.constant.CarRentalConstants.TOYOTA_CAMRY;

@Component
@Slf4j
public class StockTracker {

    @Autowired
    Map<String, Integer> initialStock;

    private final Map<String, CarStockDetail> inStockCarStockDetails = new HashMap<String, CarStockDetail>() {
        {
            put(TOYOTA_CAMRY, new CarStockDetail(TOYOTA_CAMRY, 2));
            put(BMW_650, new CarStockDetail(BMW_650, 2));
        }
    };

    public void modifyStockDetails(String carModel, int quantity, CarRentalConstants.StockAdjustInstruction action) throws RentalCarLimitExceededException {
         log.info("Car Model {}, Quantity {}, Action {}", carModel, quantity, action);

        final CarStockDetail carStockDetail = inStockCarStockDetails.get(carModel);
        if(Objects.nonNull(carStockDetail)){
            final int currentStock = inStockCarStockDetails.get(carModel).getStockSize();
            int newStock = 0;
            switch (action) {
                // In the case of returned cars
                case RETURN:
                    newStock = currentStock + quantity;
                    checkIfRentRequestMatchesStock(carModel, quantity, currentStock, newStock);
                    break;
                // In the case of rent cars
                case RENT:
                    newStock = currentStock - quantity;
                    checkIfRentRequestMatchesStock(carModel, quantity, currentStock, newStock);
                    break;
                default:
                    log.info("Action Invalid");
                    break;
            }
            inStockCarStockDetails.put(carModel, new CarStockDetail(carModel, newStock));
        } else {
            log.error("Car Model not Found");
        }
    }

    public Map<String, CarStockDetail> getAllCurrentStockDetails () {
        return inStockCarStockDetails;
    }

    // Identify if new rent/return requests can be fulfilled based on current stock
    private void checkIfRentRequestMatchesStock(String carModel, int quantity, int currentStock, int newStock) throws RentalCarLimitExceededException {
        if (newStock < 0) {
            final String errorMessage = "not enough cars for rent in store";
            throw new RentalCarLimitExceededException(carModel, quantity, errorMessage);
        } else if (newStock > currentStock && newStock > initialStock.get(carModel)) {
            final String errorMessage = "invalid operation, car limit exceed";
            throw new RentalCarLimitExceededException(carModel, quantity, errorMessage);
        }
    }

}
