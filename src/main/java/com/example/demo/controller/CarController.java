package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.DTOs.CarDTO;
import com.example.demo.DTOs.CarRequestDTO;
import com.example.demo.DTOs.PackageDTO;
import com.example.demo.DTOs.RouteDTO;
import com.example.demo.models.Car;
import com.example.demo.models.Package_;
import com.example.demo.models.Route;
import com.example.demo.repositories.*;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/car")
public class CarController {
	
	@Autowired
	CarRepository carRepo;
	
	@Autowired
	RouteRepository routeRepo;
	
	@Autowired
	PackageRepository packageRepo;
	
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
	        return ResponseEntity.notFound().build();
	    }
	    
	    // Sprawdź czy istnieją powiązane trasy
	    List<Route> routes = routeRepo.findByCar_CarId(id);
	    if (!routes.isEmpty()) {
	        return ResponseEntity
	            .badRequest()
	            .body("Nie można usunąć samochodu z przypisanymi trasami");
	    }
	    
	    carRepo.deleteById(id);
	    return ResponseEntity.noContent().build();
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


	@GetMapping("/{id}/packages")
	public ResponseEntity<CollectionModel<PackageDTO>> getCarPackages(@PathVariable Integer id) {
		if (!carRepo.existsById(id)) {
			return ResponseEntity.notFound().build();
		}

		List<Package_> packages = packageRepo.findByRoute_Car_CarId(id);
		List<PackageDTO> packagesDTO = packages.stream()
				.map(PackageDTO::new)
				.collect(Collectors.toList());

		return ResponseEntity.ok(CollectionModel.of(packagesDTO));
	}
	
	@GetMapping("/{id}/routes")
	public ResponseEntity<List<RouteDTO>> getCarRoutes(@PathVariable Integer id) {
		  List<Route> routes = routeRepo.findByCar_CarId(id); // Wymaga metody w RouteRepository
	    List<RouteDTO> routesDTO = routes.stream()
	                                     .map(RouteDTO::new)
	                                     .collect(Collectors.toList());
	    return ResponseEntity.ok(routesDTO);
	}
}
