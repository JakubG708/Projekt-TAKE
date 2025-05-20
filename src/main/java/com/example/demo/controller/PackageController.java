package com.example.demo.controller;

import com.example.demo.dto.PackageCreateDTO;
import com.example.demo.models.Package_;
import com.example.demo.models.Route;
import com.example.demo.repository.PackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
