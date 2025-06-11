package com.example.demo.controller;

import com.example.demo.DTOs.PackageCreateDTO;
import com.example.demo.DTOs.PackageResponseDTO;
import com.example.demo.models.Client;
import com.example.demo.models.Package_;
import com.example.demo.models.Route;
import com.example.demo.repositories.ClientRepository;
import com.example.demo.repositories.PackageRepository;
import com.example.demo.repositories.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/packages")
public class PackageController {
    @Autowired

    private PackageRepository packageRepo;

    @Autowired
    private ClientRepository clientRepo;

    @Autowired
    private RouteRepository routeRepo;

    @PostMapping
    public ResponseEntity<?> addPackage(@RequestBody PackageCreateDTO dto) {

        Optional<Client> clientOpt = clientRepo.findById(dto.getClientId());
        Optional<Route> routeOpt = routeRepo.findById(dto.getRouteId());
        if (clientOpt.isEmpty() || routeOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Customer or route not found.");
        }

        Package_ pack = new Package_();
        pack.setClient(clientOpt.get());
        pack.setRoute(routeOpt.get());
        pack.setStatus(dto.getStatus());
        pack.setSentDate(dto.getSentDate());
        pack.setDeliveryDate(dto.getDeliveryDate());

        packageRepo.save(pack);

        return ResponseEntity.ok("Added package with ID: " + pack.getPackageId());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPackage(@PathVariable Integer id) {
        Optional<Package_> pack = packageRepo.findById(id);
        if (pack.isPresent()) {
            PackageResponseDTO dto = new PackageResponseDTO(pack.get());
            // Links to self, client, route
            dto.add(linkTo(methodOn(PackageController.class).getPackage(id)).withSelfRel());
            dto.add(linkTo(methodOn(ClientController.class)
                    .getClient(pack.get().getClient().getClientId())).withRel("client"));
            dto.add(linkTo(methodOn(RouteController.class)
                    .getRouteById(pack.get().getRoute().getRouteId())).withRel("route"));

            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping
    public ResponseEntity<?> getAllPackages() {
        List<PackageResponseDTO> dtos = new ArrayList<>();
        for (Package_ p : packageRepo.findAll()) {
            PackageResponseDTO dto = new PackageResponseDTO(p);
            dto.add(linkTo(methodOn(PackageController.class).getPackage(p.getPackageId())).withSelfRel());
            dto.add(linkTo(methodOn(ClientController.class)
                    .getClient(p.getClient().getClientId())).withRel("client"));
            dto.add(linkTo(methodOn(RouteController.class)
                    .getRouteById(p.getRoute().getRouteId())).withRel("route"));
            dtos.add(dto);
        }
        CollectionModel<PackageResponseDTO> model = CollectionModel.of(dtos);
        model.add(linkTo(methodOn(PackageController.class).getAllPackages()).withSelfRel());
        return ResponseEntity.ok(model);
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updatePackage(@PathVariable Integer id, @RequestBody PackageCreateDTO dto) {
        Optional<Client> clientOpt = clientRepo.findById(dto.getClientId());
        Optional<Route> routeOpt = routeRepo.findById(dto.getRouteId());
        if (clientOpt.isEmpty() || routeOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Customer or route not found.");
        }
        return packageRepo.findById(id).map(existing -> {
            existing.setStatus(dto.getStatus());
            existing.setSentDate(dto.getSentDate());
            existing.setDeliveryDate(dto.getDeliveryDate());
            existing.setClient(clientOpt.get());
            existing.setRoute(routeOpt.get());
            packageRepo.save(existing);
            return ResponseEntity.ok("Updated package with ID: " + id);
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePackage(@PathVariable Integer id) {
        if (!packageRepo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        packageRepo.deleteById(id);
        return ResponseEntity.ok("Removed package with ID: " + id);
    }

    @GetMapping("/{id}/starting-point")
    public ResponseEntity<String> getPackageStartingPoint(@PathVariable Integer id) {
        return packageRepo.findById(id)
                .map(p -> ResponseEntity.ok(p.getRoute().getRouteList().getStartPoint()))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/destination")
    public ResponseEntity<String> getPackageDestination(@PathVariable Integer id) {
        return packageRepo.findById(id)
                .map(p -> {
                    Client client = p.getClient();
                    if (client != null && client.getAddress() != null) {
                        return ResponseEntity.ok(client.getAddress());
                    } else {
                        return ResponseEntity.badRequest().body("No customer or address assigned.");
                    }
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/estimated-time")
    public ResponseEntity<String> getPackageEstimatedTime(@PathVariable Integer id) {
        return packageRepo.findById(id)
                .map(p -> ResponseEntity.ok(p.getRoute().getEstimatedTime().toString()))
                .orElse(ResponseEntity.notFound().build());
    }

}
