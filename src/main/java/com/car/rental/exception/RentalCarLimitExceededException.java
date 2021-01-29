package com.car.rental.exception;

import com.car.rental.constant.CarRentalConstants;
import org.springframework.http.HttpStatus;

public class RentalCarLimitExceededException extends RentalCarGeneralException{

    public static final String RENTAL_CARS_STORAGE_ERROR = "Could not AAAA Car Model XXXX with quantity YYYY, due to ZZZZ";

    public RentalCarLimitExceededException (String carModel, int quantity, String reason, CarRentalConstants.StockAdjustInstruction action) {
        this.httpStatus = HttpStatus.NOT_ACCEPTABLE;
        this.errorMessage = RENTAL_CARS_STORAGE_ERROR.replace("AAAA", action.name()).replace("XXXX", carModel).replace("YYYY", String.valueOf(quantity)).replace("ZZZZ", reason);
    }
}
