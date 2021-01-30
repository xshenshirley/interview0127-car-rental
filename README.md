# interview0127-car-rental

A basic Car Rental Application allows user to view current stock of cars, and rent or return cars based on requirement

# Service API Details

a) GET: getAllCarsInStock – Provides user with details of cars that are currently in stock for rent

b) POST: rentCars – Allow user to rent/return cars

example contract:

{

    "carModel" : "Toyota Camry",
    "quantity": 1,
    "instruction": "RENT" //Instruction for RENT/RETURN cars
}

# Local Setup

* Clone this repo to your local computer **`git clone https://github.com/xshenshirley/interview0127-car-rental.git`**
* run **`mvn spring-boot:run`** from the cloned repo's root directory
* Open your browser and go to Swagger UI [**`http://localhost:8080/swagger-ui.html#/`**](http://localhost:8080/swagger-ui.html#/) or test with [**`http://localhost:8080`**](http://localhost:8080)


# Example Failure Scenarios:

a)	Could not rent Car Model Toyota Camry, due to not
enough cars for rent in store

b) Could not return Car Model Toyota Camry, due to invalid      operation, car limit exceed

