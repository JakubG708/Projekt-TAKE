package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.models.Car;
import com.example.demo.repositories.*;


@RestController
@RequestMapping("/car")
public class CarController {
	
	@Autowired
	CarRepository carRepo;
	@PostMapping("/add")
	public @ResponseBody String addCar(@RequestBody Car car) 
	{
		 carRepo.save(car);
		 return "Dodano auto z ID: " + car.getCarId();
	}
	
	
	@GetMapping("/brand/{brand}")
	public ResponseEntity<List<Car>> getBrand(@PathVariable String brand) {
	    List<Car> cars = carRepo.findByBrand(brand);
	    
	    if (cars.isEmpty()) {
	        return ResponseEntity.notFound().build(); // HTTP 404, jeśli brak wyników
	    }
	    return ResponseEntity.ok(cars); // HTTP 200 z listą samochodów
	}
	
	
	@GetMapping("/{id}")
	public Car getCarId(@PathVariable Integer id) 
	{
	    return carRepo.findById(id).orElse(null);
	}
	
	@GetMapping("/cars")
	public @ResponseBody Iterable<Car> getCars() 
	{
		return carRepo.findAll();
	}
}
