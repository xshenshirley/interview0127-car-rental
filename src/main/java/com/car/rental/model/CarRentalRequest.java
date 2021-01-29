package com.car.rental.model;

import com.car.rental.constant.CarRentalConstants;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CarRentalRequest {

    private String carModel;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    private int quantity;
    private CarRentalConstants.StockAdjustInstruction instruction;
}
