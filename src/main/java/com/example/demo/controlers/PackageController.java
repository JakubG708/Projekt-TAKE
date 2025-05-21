package com.example.demo.controlers;



import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.models.*;
import com.example.demo.repositories.*;

import jakarta.validation.Valid;

import com.example.demo.DTOs.*;




@RestController
@RequestMapping("/package")
public class PackageController {
	
	@Autowired
    private PackageRepository packageRepo;

    @Autowired
    private ClientRepository clientRepo;

    @Autowired
    private RouteRepository routeRepo;

    @Autowired
    private CarRepository carRepo;
	
	
	 @GetMapping("/{id}")
    public ResponseEntity<PackageDTO> getPackageById(@PathVariable Integer id) {
        Optional<Package_> package_ = packageRepo.findById(id);
        return package_.map(p -> new PackageDTO(p))
                        .map(ResponseEntity::ok)
                        .orElse(ResponseEntity.notFound().build());
    }
	 
	 
	 @PostMapping("/add")
    public ResponseEntity<PackageDTO> addPackage(
            @Valid @RequestBody PackageRequestDTO packageRequest
    ) {
        Optional<Client> client = clientRepo.findById(packageRequest.getClientId());
        Optional<Route> route = routeRepo.findById(packageRequest.getRouteId());
        Optional<Car> car = carRepo.findById(packageRequest.getCarId());


        if (client.isEmpty() || route.isEmpty() || car.isEmpty()) {
            return ResponseEntity.notFound().build();
        }


        Package_ newPackage = new Package_();
        newPackage.setClient(client.get());
        newPackage.setRoute(route.get());
        newPackage.setCar(car.get());
        newPackage.setStatus(packageRequest.getStatus());
        newPackage.setSentDate(packageRequest.getSentDate());
        newPackage.setDeliveryDate(packageRequest.getDeliveryDate());


        Package_ savedPackage = packageRepo.save(newPackage);


        PackageDTO packageDTO = new PackageDTO(savedPackage);
        
        return ResponseEntity
                .created(packageDTO.getRequiredLink("self").toUri()) // URI z nagłówka Location
                .body(packageDTO);
    }

}
