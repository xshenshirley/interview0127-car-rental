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

Example Failure Scenarios:

a)	Could not rent Car Model Toyota Camry with quantity 1, due to not
enough cars for rent in store

b) Could not rent Car Model Toyota Camry with quantity 1, due to invalid      operation, car limit exceed

