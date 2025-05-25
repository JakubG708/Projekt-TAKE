package com.example.demo.controller;

import com.example.demo.DTOs.RouteCreateDTO;
import com.example.demo.models.Car;
import com.example.demo.models.Route;
import com.example.demo.repositories.CarRepository;
import com.example.demo.repositories.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/routes")
public class RouteController {
    @Autowired
    private RouteRepository routeRepo;

    @Autowired
    private CarRepository carRepo;

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
}
