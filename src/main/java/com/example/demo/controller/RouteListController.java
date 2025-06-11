package com.example.demo.controller;

import com.example.demo.DTOs.PackageResponseDTO;
import com.example.demo.DTOs.RouteDTO;
import com.example.demo.DTOs.RouteListCreateDTO;
import com.example.demo.DTOs.RouteListDTO;
import com.example.demo.models.Package_;
import com.example.demo.models.Route;
import com.example.demo.models.RouteList;
import com.example.demo.repositories.PackageRepository;
import com.example.demo.repositories.RouteListRepository;
import com.example.demo.repositories.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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

        return ResponseEntity.ok("Route section with ID: " + routeList.getRouteListId() + " added." );
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
            return ResponseEntity.ok("Route section with ID: " + existing.getRouteListId() + " updated." );
        }).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RouteListDTO> getRouteList(@PathVariable Integer id) {
        return routeListRepo.findById(id)
                .map(routeList -> {
                    RouteListDTO dto = new RouteListDTO(routeList);
                    dto.add(linkTo(methodOn(RouteListController.class).getRouteList(id)).withSelfRel());
                    dto.add(linkTo(methodOn(RouteListController.class).getRoutesByRouteList(id)).withRel("routes"));
                    dto.add(linkTo(methodOn(RouteListController.class).getPackagesByRouteList(id)).withRel("packages"));
                    return ResponseEntity.ok(dto);
                })
                .orElse(ResponseEntity.notFound().build());
    }


    @GetMapping("/{id}/routes")
    public ResponseEntity<CollectionModel<RouteDTO>> getRoutesByRouteList(@PathVariable Integer id) {
        List<Route> routes = routeRepo.findByRouteList_RouteListId(id);
        List<RouteDTO> result = new ArrayList<>();
        for (Route route : routes) {
            RouteDTO dto = new RouteDTO(route);
            dto.add(linkTo(methodOn(RouteController.class).getRouteById(route.getRouteId())).withSelfRel());
            result.add(dto);
        }
        CollectionModel<RouteDTO> model = CollectionModel.of(result);
        return ResponseEntity.ok(model);
    }

    @GetMapping("/{id}/packages")
    public ResponseEntity<CollectionModel<PackageResponseDTO>> getPackagesByRouteList(@PathVariable Integer id) {
        List<Package_> packages = packageRepo.findByRoute_RouteList_RouteListId(id);
        List<PackageResponseDTO> result = new ArrayList<>();
        for (Package_ p : packages) {
            PackageResponseDTO dto = new PackageResponseDTO(p);
            dto.add(linkTo(methodOn(PackageController.class).getPackage(p.getPackageId())).withSelfRel());
            result.add(dto);
        }
        CollectionModel<PackageResponseDTO> model = CollectionModel.of(result);
        return ResponseEntity.ok(model);
    }


}

