package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


import com.example.demo.DTOs.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.models.Car;
import com.example.demo.models.Package_;
import com.example.demo.models.Route;
import com.example.demo.repositories.*;

import jakarta.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


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
		)
	{
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
	public ResponseEntity<CollectionModel<CarDTO>> getCars() {
		List<CarDTO> dtos = new ArrayList<>();
		for (Car c : carRepo.findAll()) {
			CarDTO dto = new CarDTO(c);
			dto.add(linkTo(methodOn(CarController.class).getCar(c.getCarId())).withSelfRel());
			dtos.add(dto);
		}
		CollectionModel<CarDTO> model = CollectionModel.of(dtos);
		model.add(linkTo(methodOn(CarController.class).getCars()).withSelfRel());
		return ResponseEntity.ok(model);
	}

	
	@GetMapping("/{id}")
	public ResponseEntity<CarDTO> getCar(@PathVariable Integer id) 
	{
		Optional<Car> carOptional = carRepo.findById(id);
		
		if (carOptional.isEmpty()) {
	        return ResponseEntity.notFound().build(); // HTTP 404
	    }
		CarDTO dto = new CarDTO(carOptional.get());

		dto.add(linkTo(methodOn(CarController.class).getCar(id)).withSelfRel());
		dto.add(linkTo(methodOn(CarController.class).getCars()).withRel("all-cars"));
		dto.add(linkTo(methodOn(CarController.class).getCarPackages(id)).withRel("packages"));
		dto.add(linkTo(methodOn(CarController.class).getCarRoutes(id)).withRel("routes"));

		return ResponseEntity.ok(dto);
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
		List<PackageDTO> packagesDTO = new ArrayList<>();
		for (Package_ p : packages) {
			PackageDTO dto = new PackageDTO(p);
			dto.add(linkTo(methodOn(PackageController.class).getPackage(p.getPackageId())).withSelfRel());
			packagesDTO.add(dto);
		}
		CollectionModel<PackageDTO> model = CollectionModel.of(packagesDTO);
		return ResponseEntity.ok(model);
	}

	@GetMapping("/{id}/routes")
	public ResponseEntity<CollectionModel<RouteDTO>> getCarRoutes(@PathVariable Integer id) {
		List<Route> routes = routeRepo.findByCar_CarId(id);
		List<RouteDTO> routesDTO = new ArrayList<>();
		for (Route route : routes) {
			RouteDTO dto = new RouteDTO(route);
			dto.add(linkTo(methodOn(RouteController.class).getRouteById(route.getRouteId())).withSelfRel());
			routesDTO.add(dto);
		}
		CollectionModel<RouteDTO> model = CollectionModel.of(routesDTO);
		return ResponseEntity.ok(model);
	}


	@GetMapping("/{id}/estimated-time")
	public ResponseEntity<String> getEstimatedTime(@PathVariable Integer id) {
		if (!carRepo.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		List<Route> routes = routeRepo.findByCar_CarId(id);
		int totalSeconds = routes.stream()
				.map(r -> r.getEstimatedTime().toSecondOfDay())
				.mapToInt(i -> i)
				.sum();
		int hours = totalSeconds / 3600;
		int minutes = (totalSeconds % 3600) / 60;
		return ResponseEntity.ok(hours + "h " + minutes + "min");
	}

	@GetMapping("/{id}/total-distance")
	public ResponseEntity<String> getTotalDistance(@PathVariable Integer id) {
		List<Route> routes = routeRepo.findByCar_CarId(id);
		double totalDistance = routes.stream()
				.map(r -> r.getRouteList().getDistance())
				.mapToDouble(d -> d)
				.sum();
		return ResponseEntity.ok(totalDistance + " km");
	}

	@GetMapping("/{id}/starting-points")
	public ResponseEntity<List<RouteStartingPointDTO>> getCarStartingPoints(@PathVariable Integer id) {
		if (!carRepo.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		List<Route> routes = routeRepo.findByCar_CarId(id);
		List<RouteStartingPointDTO> result = new ArrayList<>();
		for (Route r : routes) {
			result.add(new RouteStartingPointDTO(r.getRouteId(), r.getRouteList().getStartPoint()));
		}
		return ResponseEntity.ok(result);
	}

}
