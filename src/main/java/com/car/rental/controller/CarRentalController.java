package com.car.rental.controller;

import com.car.rental.exception.RentalCarLimitExceededException;
import com.car.rental.model.CarRentalRequest;
import com.car.rental.model.CarStockDetail;
import com.car.rental.model.CarStockDetailsResponse;
import com.car.rental.service.CarBookingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
@Api(value = "/api")
public class CarRentalController {

    @Autowired
    private CarBookingService carBookingService;

    /**
     *
     * This method returns all available cars in store
     *
     **/
    @RequestMapping(path="/getAllCarsInStock", method= RequestMethod.GET)
    @ApiOperation(value = "Get All Cars In Stock",
            response = CarStockDetailsResponse.class
    )
    public CarStockDetailsResponse getAllInStockCars(){
        CarStockDetailsResponse carStockDetailsResponse= new CarStockDetailsResponse();
        carStockDetailsResponse.setCarStockDetails(carBookingService.getAllInStockCars());
        carStockDetailsResponse.setSuccess(true);
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
    @ApiOperation(value = "Rent/Return Cars In Stock",
            response = CarStockDetailsResponse.class
    )
    public CarStockDetailsResponse rentCarsInStock(@RequestBody @ApiParam(value = "parameter") final CarRentalRequest carRentalRequest){
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
