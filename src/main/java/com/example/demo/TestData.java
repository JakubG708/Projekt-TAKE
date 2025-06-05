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
    private final RouteListRepository routeListRepo;
    private final RouteRepository routeRepo;
    private final ClientRepository clientRepo;
    private final PackageRepository packageRepo;

    public TestData(
            CarRepository carRepo,
            RouteListRepository routeListRepo,
            RouteRepository routeRepo,
            ClientRepository clientRepo,
            PackageRepository packageRepo) {
        this.carRepo = carRepo;
        this.routeListRepo = routeListRepo;
        this.routeRepo = routeRepo;
        this.clientRepo = clientRepo;
        this.packageRepo = packageRepo;
    }

    @Override
    public void run(String... args) {

        Car car1 = new Car();
        car1.setBrand("Toyota");
        carRepo.save(car1);

        Car car2 = new Car();
        car2.setBrand("Ford");
        carRepo.save(car2);

        Car car3 = new Car();
        car3.setBrand("Opel");
        carRepo.save(car3);

        Client client1 = new Client();
        client1.setFirstName("Jan");
        client1.setLastName("Kowalski");
        client1.setAddress("ul. Kwiatowa 5, Kraków");
        client1.setPhone("123456789");
        client1.setEmail("jan.kowalski@example.com");
        clientRepo.save(client1);

        Client client2 = new Client();
        client2.setFirstName("Anna");
        client2.setLastName("Nowak");
        client2.setAddress("ul. Leśna 10, Warszawa");
        client2.setPhone("987654321");
        client2.setEmail("anna.nowak@example.com");
        clientRepo.save(client2);

        Client client3 = new Client();
        client3.setFirstName("Piotr");
        client3.setLastName("Wójcik");
        client3.setAddress("ul. Słoneczna 8, Poznań");
        client3.setPhone("555111222");
        client3.setEmail("piotr.wojcik@example.com");
        clientRepo.save(client3);

        RouteList rl1 = new RouteList();
        rl1.setStartPoint("Kraków");
        rl1.setDestinationPoint("Warszawa");
        rl1.setDistance(300.0);
        routeListRepo.save(rl1);

        RouteList rl2 = new RouteList();
        rl2.setStartPoint("Warszawa");
        rl2.setDestinationPoint("Poznań");
        rl2.setDistance(310.0);
        routeListRepo.save(rl2);

        RouteList rl3 = new RouteList();
        rl3.setStartPoint("Kraków");
        rl3.setDestinationPoint("Katowice");
        rl3.setDistance(80.0);
        routeListRepo.save(rl3);

        Route route1 = new Route();
        route1.setCar(car1);
        route1.setRouteList(rl1);
        route1.setEstimatedTime(LocalTime.of(2, 30));
        routeRepo.save(route1);

        Route route2 = new Route();
        route2.setCar(car2);
        route2.setRouteList(rl2);
        route2.setEstimatedTime(LocalTime.of(3, 0));
        routeRepo.save(route2);

        Route route3 = new Route();
        route3.setCar(car3);
        route3.setRouteList(rl3);
        route3.setEstimatedTime(LocalTime.of(1, 15));
        routeRepo.save(route3);

        Route route4 = new Route();
        route4.setCar(car1);
        route4.setRouteList(rl3);
        route4.setEstimatedTime(LocalTime.of(1, 5));
        routeRepo.save(route4);

        Package_ p1 = new Package_();
        p1.setClient(client1);
        p1.setRoute(route1);
        p1.setStatus("SENT");
        p1.setSentDate(LocalDate.now().minusDays(1));
        p1.setDeliveryDate(LocalDate.now());
        packageRepo.save(p1);

        Package_ p2 = new Package_();
        p2.setClient(client2);
        p2.setRoute(route2);
        p2.setStatus("IN DELIVERY");
        p2.setSentDate(LocalDate.now());
        p2.setDeliveryDate(LocalDate.now().plusDays(1));
        packageRepo.save(p2);

        Package_ p3 = new Package_();
        p3.setClient(client3);
        p3.setRoute(route3);
        p3.setStatus("DELIVERED");
        p3.setSentDate(LocalDate.now().minusDays(2));
        p3.setDeliveryDate(LocalDate.now().minusDays(1));
        packageRepo.save(p3);

        Package_ p4 = new Package_();
        p4.setClient(client1);
        p4.setRoute(route4);
        p4.setStatus("IN DELIVERY");
        p4.setSentDate(LocalDate.now());
        p4.setDeliveryDate(LocalDate.now().plusDays(2));
        packageRepo.save(p4);
    }
}
