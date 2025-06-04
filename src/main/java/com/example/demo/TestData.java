package com.example.demo;

import com.example.demo.models.*;
import com.example.demo.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.time.LocalTime;

@Component
public class TestData implements CommandLineRunner {

    private final CarRepository carRepo;
    //private final RouteListRepository routeListRepo;
    private final RouteRepository routeRepo;
    private final ClientRepository clientRepo;
    private final PackageRepository packageRepo;

    public TestData(
            CarRepository carRepo,
            //RouteListRepository routeListRepo,
            RouteRepository routeRepo,
            ClientRepository clientRepo,
            PackageRepository packageRepo) {
        this.carRepo = carRepo;
        //this.routeListRepo = routeListRepo;
        this.routeRepo = routeRepo;
        this.clientRepo = clientRepo;
        this.packageRepo = packageRepo;
    }

    @Override
    public void run(String... args) {

        // Dodaj samochód
        Car car = new Car();
        car.setBrand("Toyota");
        carRepo.save(car);

        // Dodaj klienta
        Client client = new Client();
        client.setFirstName("Jan");
        client.setLastName("Kowalski");
        client.setAddress("ul. Kwiatowa 5");
        client.setPhone("123456789");
        client.setEmail("jan@example.com");
        clientRepo.save(client);

        // Dodaj listę tras
        RouteList rl = new RouteList();
        rl.setStartPoint("Kraków");
        rl.setDestinationPoint("Warszawa");
        rl.setDistance(300.0);
        //routeListRepo.save(rl);

        // Dodaj trasę
        Route route = new Route();
        route.setCar(car);
        //route.setRouteList(rl);
        route.setEstimatedTime(LocalTime.of(2, 30));
        routeRepo.save(route);

        // Dodaj paczkę
        Package_ p = new Package_();
        p.setClient(client);
        p.setRoute(route);
        p.setStatus("WYSŁANA");
        p.setSentDate(LocalDate.now());
        p.setDeliveryDate(LocalDate.now().plusDays(1));
        packageRepo.save(p);
    }
}
