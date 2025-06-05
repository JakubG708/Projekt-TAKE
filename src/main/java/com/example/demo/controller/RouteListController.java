package com.example.demo.controller;

import com.example.demo.DTOs.PackageResponseDTO;
import com.example.demo.DTOs.RouteDTO;
import com.example.demo.DTOs.RouteListCreateDTO;
import com.example.demo.models.Package_;
import com.example.demo.models.Route;
import com.example.demo.models.RouteList;
import com.example.demo.repositories.PackageRepository;
import com.example.demo.repositories.RouteListRepository;
import com.example.demo.repositories.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/routelist")
public class RouteListController {

    @Autowired
    private RouteListRepository routeListRepo;

    @Autowired
    private RouteRepository routeRepo;

    @Autowired
    private PackageRepository packageRepo;

    @PostMapping
    public ResponseEntity<?> addRouteList(@RequestBody RouteListCreateDTO request) {
        RouteList routeList = new RouteList();
        routeList.setStartPoint(request.getStartPoint());
        routeList.setDestinationPoint(request.getDestinationPoint());
        routeList.setDistance(request.getDistance());

        RouteList saved = routeListRepo.save(routeList);

        return ResponseEntity.ok("Route section with ID added: " + routeList.getRouteListId());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRouteList(@PathVariable Integer id) {
        if (!routeListRepo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        routeListRepo.deleteById(id);
        return ResponseEntity.ok("Deleted section ID: " + id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRouteList(@PathVariable Integer id, @RequestBody RouteListCreateDTO updated) {
        return routeListRepo.findById(id).map(existing -> {
            existing.setStartPoint(updated.getStartPoint());
            existing.setDestinationPoint(updated.getDestinationPoint());
            existing.setDistance(updated.getDistance());
            routeListRepo.save(existing);
            return ResponseEntity.ok(existing);
        }).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RouteList> getRouteList(@PathVariable Integer id) {
        return routeListRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/routes")
    public ResponseEntity<List<RouteDTO>> getRoutesByRouteList(@PathVariable Integer id) {
        List<Route> routes = routeRepo.findByRouteList_RouteListId(id);
        List<RouteDTO> result = new ArrayList<>();
        for (Route route : routes) {
            RouteDTO dto =  new RouteDTO(route);
            result.add(dto);
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}/packages")
    public ResponseEntity<List<PackageResponseDTO>> getPackagesByRouteList(@PathVariable Integer id) {
        List<Package_> packages = packageRepo.findByRoute_RouteList_RouteListId(id);
        List<PackageResponseDTO> result = new ArrayList<>();
        for (Package_ p : packages) {
            PackageResponseDTO dto = new PackageResponseDTO(p);
            result.add(dto);
        }
        return ResponseEntity.ok(result);
    }

}

