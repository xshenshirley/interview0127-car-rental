package com.car.rental.controller;

import com.car.rental.exception.RentalCarLimitExceededException;
import com.car.rental.model.CarRentalRequest;
import com.car.rental.model.CarStockDetail;
import com.car.rental.model.CarStockDetailsResponse;
import com.car.rental.service.CarBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xinyishen on 2021/1/26.
 */
@RestController
@RequestMapping(path="/api")
public class CarRentalController {

    @Autowired
    private CarBookingService carBookingService;

    /**
     *
     * This method returns all available cars in store
     *
     **/
    @RequestMapping(path="/getAllCarsInStock", method= RequestMethod.GET)
    public CarStockDetailsResponse getAllInStockCars(){
        CarStockDetailsResponse carStockDetailsResponse= new CarStockDetailsResponse();
        carStockDetailsResponse.setCarStockDetails(carBookingService.getAllInStockCars());

        return carStockDetailsResponse;
    }

    /**
     *
     * This method performs rent cars action based on current stock
     * @param carRentalRequest
     * @return CarStockDetailsResponse
     *
     **/
    @PostMapping(path="/rentCars", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public CarStockDetailsResponse rentCarsInStock(@RequestBody final CarRentalRequest carRentalRequest){
        CarStockDetailsResponse carStockDetailsResponse= new CarStockDetailsResponse();

        try {
            List<CarStockDetail> postActionCarStockDetails = carBookingService.rentCarsInStock(carRentalRequest.getCarModel(), carRentalRequest.getQuantity(), carRentalRequest.getInstruction());
            carStockDetailsResponse.setCarStockDetails(postActionCarStockDetails);
            carStockDetailsResponse.setSuccess(true);
        } catch (RentalCarLimitExceededException e) {
            carStockDetailsResponse.setSuccess(false);
            carStockDetailsResponse.setError(e.getErrorMessage());
            carStockDetailsResponse.setCarStockDetails(new ArrayList<>());
        }

        return carStockDetailsResponse;
    }

}
