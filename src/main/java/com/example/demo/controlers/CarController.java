package com.example.demo.controlers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.DTOs.CarDTO;
import com.example.demo.DTOs.CarRequestDTO;
import com.example.demo.DTOs.ClientDTO;
import com.example.demo.DTOs.ClientRequestDTO;
import com.example.demo.models.Car;
import com.example.demo.models.Client;
import com.example.demo.repositories.*;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/car")
public class CarController {
	
	@Autowired
	CarRepository carRepo;
	@PostMapping("/add")
	public ResponseEntity<CarDTO> addCar(
		    @Valid @RequestBody CarDTO carRequest
		) {
		    // Mapowanie DTO -> Encja
		    Car newCar = new Car();
		    newCar.setBrand(carRequest.getBrand());

		    Car savedCar = carRepo.save(newCar);
		   
		    return ResponseEntity.ok(new CarDTO(savedCar));
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteCar(@PathVariable Integer id) {
	    Optional<Car> carOptional = carRepo.findById(id);
	    
	    if (carOptional.isEmpty()) {
	        return ResponseEntity.notFound().build(); // HTTP 404 - Samochod nie istnieje
	    }
	    
	    carRepo.deleteById(id);
	    return ResponseEntity.noContent().build(); // HTTP 204 - Usunięto pomyślnie
	}
	
	@GetMapping("/cars")
	public @ResponseBody CollectionModel<CarRequestDTO> getCars() 
	{
		List<CarRequestDTO> carsDTO = new ArrayList<>();
		for(Car c:carRepo.findAll())
		{
			carsDTO.add(new CarRequestDTO(c));
		}
		return CollectionModel.of(carsDTO);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<CarDTO> getCar(@PathVariable Integer id) 
	{
		Optional<Car> carOptional = carRepo.findById(id);
		
		if (carOptional.isEmpty()) {
	        return ResponseEntity.notFound().build(); // HTTP 404
	    }
		
		Car c = carOptional.get();
		
		return ResponseEntity.ok(new CarDTO(c));
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<CarDTO> updateCar(
	    @PathVariable Integer id,
	    @Valid @RequestBody CarDTO updateDTO
	) {
	    Optional<Car> carOptional = carRepo.findById(id);
	    
	    if (carOptional.isEmpty()) {
	        return ResponseEntity.notFound().build(); // HTTP 404
	    }

	    Car existingCar = carOptional.get();
	    
	    existingCar.setBrand(updateDTO.getBrand());


	    Car updatedCar = carRepo.save(existingCar);
	    
	    return ResponseEntity.ok(new CarDTO(updatedCar)); // HTTP 200
	}
}
