package com.example.demo;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


import com.example.demo.models.*;

@RestController
@CrossOrigin
public class isItWorking {

	@GetMapping("/test")
	 public Car test() {
        Car car = new Car();
        car.setCarId(1);
        car.setBrand("Toyota");

        return car;  // Zostanie automatycznie zamieniony na JSON
    }
}
