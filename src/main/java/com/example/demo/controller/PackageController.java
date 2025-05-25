package com.example.demo.controller;

import com.example.demo.DTOs.PackageCreateDTO;
import com.example.demo.models.Package_;
import com.example.demo.repositories.PackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/packages")
public class PackageController {
    @Autowired
    private PackageRepository packageRepo;

/*    @Autowired
    private ClientRepository clientRepo;

    @Autowired
    private RouteRepository routeRepo;*/

    @PostMapping
    public ResponseEntity<?> addPackage(@RequestBody PackageCreateDTO dto) {

        // FOR TEST - CLIENT AND ROUTE = NULL
        //Optional<Client> clientOpt = clientRepo.findById(dto.getClientId());
        //Optional<Route> routeOpt = routeRepo.findById(dto.getRouteId());

/*        if (clientOpt.isEmpty() || routeOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Nie znaleziono klienta lub trasy.");
        }*/

        Package_ pack = new Package_();
        pack.setClient(null);
        pack.setRoute(null);
        pack.setStatus(dto.getStatus());
        pack.setSentDate(dto.getSentDate());
        pack.setDeliveryDate(dto.getDeliveryDate());

        packageRepo.save(pack);

        return ResponseEntity.ok("Dodano paczkę o ID: " + pack.getPackageId());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getPackage(@PathVariable Integer id) {
        Optional<Package_> pack = packageRepo.findById(id);
        if (pack.isPresent()) {
            return ResponseEntity.ok(pack.get());
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping
    public Iterable<Package_> getAllPackages() {
        return packageRepo.findAll();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePackage(@PathVariable Integer id, @RequestBody PackageCreateDTO dto) {
        return packageRepo.findById(id).map(existing -> {
            existing.setStatus(dto.getStatus());
            existing.setSentDate(dto.getSentDate());
            existing.setDeliveryDate(dto.getDeliveryDate());
            // Później dodać clienta i trase
            packageRepo.save(existing);
            return ResponseEntity.ok("Zaktualizowano paczkę o ID: " + id);
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePackage(@PathVariable Integer id) {
        if (!packageRepo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        packageRepo.deleteById(id);
        return ResponseEntity.ok("Usunięto paczkę o ID: " + id);
    }
}
