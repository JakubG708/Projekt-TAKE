package pl.polsl.lab1.gruszka.wiktor.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.polsl.lab1.gruszka.wiktor.DTOs.RouteDTO;
import pl.polsl.lab1.gruszka.wiktor.DTOs.RouteInfoDTO;
import pl.polsl.lab1.gruszka.wiktor.DTOs.PackageDTO;
import pl.polsl.lab1.gruszka.wiktor.DTOs.CarDTO;
import pl.polsl.lab1.gruszka.wiktor.models.Route;
import pl.polsl.lab1.gruszka.wiktor.models.Car;
import pl.polsl.lab1.gruszka.wiktor.repositories.RouteRepository;
import pl.polsl.lab1.gruszka.wiktor.repositories.CarRepository;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/routes")
public class RouteController {

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private CarRepository carRepository;

    // 24. Edycja trasy
    @PutMapping("/{id}")
    public RouteDTO updateRoute(@PathVariable Long id, @RequestBody RouteDTO routeDTO) {
        Route route = routeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Route not found"));
        route.setStartLocation(routeDTO.getStartLocation());
        route.setEndLocation(routeDTO.getEndLocation());
        route.setDistance(routeDTO.getDistance());
        route.setEstimatedTime(routeDTO.getEstimatedTime());
        route.setCar(carRepository.findById(routeDTO.getCarId()).orElse(null));
        routeRepository.save(route);
        routeDTO.setId(route.getId());
        return routeDTO;
    }

    // 25. Info o trasie
    @GetMapping("/{id}/info")
    public RouteInfoDTO getRouteInfo(@PathVariable Long id) {
        Route route = routeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Route not found"));
        return new RouteInfoDTO(
            route.getId(),
            route.getEstimatedTime(),
            route.getCar() != null ? route.getCar().getModel() : null,
            route.getStartLocation(),
            route.getEndLocation()
        );
    }

    // 26. Wszystkie paczki przewożone na trasie
    @GetMapping("/{id}/packages")
    public List<PackageDTO> getPackagesOnRoute(@PathVariable Long id) {
        Route route = routeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Route not found"));
        return route.getPackages().stream()
            .map(pkg -> new PackageDTO(pkg.getId(), pkg.getWeight(), pkg.getStatus()))
            .collect(Collectors.toList());
    }

    // 27. Samochód przypisany do trasy
    @GetMapping("/{id}/car")
    public CarDTO getCarForRoute(@PathVariable Long id) {
        Route route = routeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Route not found"));
        Car car = route.getCar();
        if (car == null) return null;
        return new CarDTO(car.getId(), car.getModel(), car.getCapacity());
    }

    // 28. Dodanie nowej trasy
    @PostMapping
    public RouteDTO createRoute(@RequestBody RouteDTO routeDTO) {
        Route route = new Route();
        route.setStartLocation(routeDTO.getStartLocation());
        route.setEndLocation(routeDTO.getEndLocation());
        route.setDistance(routeDTO.getDistance());
        route.setEstimatedTime(routeDTO.getEstimatedTime());
        route.setCar(carRepository.findById(routeDTO.getCarId()).orElse(null));
        routeRepository.save(route);
        routeDTO.setId(route.getId());
        return routeDTO;
    }

    // 29. Usuwanie trasy
    @DeleteMapping("/{id}")
    public void deleteRoute(@PathVariable Long id) {
        routeRepository.deleteById(id);
    }

    // 30. Edycja pozycji na liście tras (jeśli masz pole position)
    @PutMapping("/{id}/position")
    public void updateRoutePosition(@PathVariable Long id, @RequestBody int newPosition) {
        Route route = routeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Route not found"));
        route.setPosition(newPosition); // Pole position musi być w modelu Route
        routeRepository.save(route);
    }
}
