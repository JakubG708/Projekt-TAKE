package com.example.demo.controller;

import com.example.demo.DTOs.CarDTO;
import com.example.demo.DTOs.ClientContactInformationDTO;
import com.example.demo.DTOs.ClientDTO;
import com.example.demo.DTOs.ClientRequestDTO;
import com.example.demo.DTOs.PackageForRouteDTO;
import com.example.demo.DTOs.RouteCreateDTO;
import com.example.demo.DTOs.RouteDTO;
import com.example.demo.DTOs.RouteRequestDTO;
import com.example.demo.models.Car;
import com.example.demo.models.Client;
import com.example.demo.models.Package_;
import com.example.demo.models.Route;
import com.example.demo.models.RouteList;
import com.example.demo.repositories.CarRepository;
import com.example.demo.repositories.PackageRepository;
import com.example.demo.repositories.RouteListRepository;
import com.example.demo.repositories.RouteRepository;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/routes")
public class RouteController {
    @Autowired
    private RouteRepository routeRepo;

    @Autowired
    private CarRepository carRepo;
    
    @Autowired
    private PackageRepository packageRepo;
    
    
    @Autowired
    private RouteListRepository routeListRepo;

//    @Autowired
//    private RouteListRepository routeListRepo;

    @PostMapping("/add")
    public ResponseEntity<?> addRoute(@RequestBody RouteCreateDTO dto) {
        Optional<Car> carOpt = carRepo.findById(dto.getCarId());
        //Optional<RouteList> listOpt = routeListRepo.findById(dto.getRouteListId());

        if (carOpt.isEmpty() /*|| listOpt.isEmpty()*/) {
            return ResponseEntity.badRequest().body("Nie znaleziono auta lub listy trasy.");
        }

        Route route = new Route();
        route.setCar(carOpt.get());
        // null for test
        route.setRouteList(null);
        route.setEstimatedTime(dto.getEstimatedTime());

        routeRepo.save(route);

        return ResponseEntity.ok("Dodano trasę o ID: " + route.getRouteId());
    }
    
    
    
    
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateClient(@PathVariable Integer id, @RequestBody RouteRequestDTO updateDTO) {
        Optional<Route> routeOptional = routeRepo.findById(id);
        if (routeOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Optional<Car> carOptional = carRepo.findById(updateDTO.getCarId());
        if (carOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid car ID");
        }

        Route existingRoute = routeOptional.get();
        existingRoute.setEstimatedTime(updateDTO.getEstimatedTime());
        existingRoute.setCar(carOptional.get());

        if (updateDTO.getRouteListId() != null) {
            Optional<RouteList> routeListOptional = routeListRepo.findById(updateDTO.getRouteListId());
            if (routeListOptional.isEmpty()) {
                return ResponseEntity.badRequest().body("Invalid route list ID");
            }
            existingRoute.setRouteList(routeListOptional.get());
        } else {
            existingRoute.setRouteList(null); 
        }

        Route updatedRoute = routeRepo.save(existingRoute);

        return ResponseEntity.ok().build();
    }
    
    
    @DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteCar(@PathVariable Integer id) 
    {
	    Optional<Route> routeOptional = routeRepo.findById(id);
	    
	    if (routeOptional.isEmpty()) {
	    	return ResponseEntity.badRequest().body("Nie znaleziono scierzki do usuniecia");
	    }
	    

	    List<Package_> packages = packageRepo.findByRoute_RouteId(id);
	    for (Package_ pkg : packages) {
	        pkg.setRoute(null); 
	    }
	    packageRepo.saveAll(packages);
	    
	    routeRepo.deleteById(id);
	    return ResponseEntity.ok().body("Scierzka o ID: " +id+" zostala usunieta");
	}
    
    
    @GetMapping("/{id}")
    public ResponseEntity<RouteDTO> getRouteById(@PathVariable Integer id) {
        Optional<Route> routeOptional = routeRepo.findById(id);

        if (routeOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Route route = routeOptional.get();
        RouteDTO routeDTO = new RouteDTO(route);

        return ResponseEntity.ok(routeDTO);
    }
    
    @GetMapping("/{id}/packages")
    public ResponseEntity<List<PackageForRouteDTO>> getPackagesByRouteId(@PathVariable Integer id) {
        Optional<Route> routeOpt = routeRepo.findById(id);

        if (routeOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Route route = routeOpt.get();
        List<Package_> packages = packageRepo.findByRoute(route);
        List<PackageForRouteDTO> packageDTOs = packages.stream()
                                               .map(PackageForRouteDTO::new)
                                               .toList();

        return ResponseEntity.ok(packageDTOs);
    }
    
    
    @GetMapping("/{id}/car")
    public ResponseEntity<CarDTO> getCarByRouteId(@PathVariable Integer id) {
        Optional<Route> routeOptional = routeRepo.findById(id);

        if (routeOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Car car = routeOptional.get().getCar();

        if (car == null) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(new CarDTO(car));
    }


}
