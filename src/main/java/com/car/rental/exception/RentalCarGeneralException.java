package com.car.rental.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class RentalCarGeneralException extends Exception{

    protected HttpStatus httpStatus;
    public String errorMessage;

}
