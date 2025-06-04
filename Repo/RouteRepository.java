RouteRepository.java
package pl.polsl.lab1.gruszka.wiktor.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.polsl.lab1.gruszka.wiktor.models.Route;

public interface RouteRepository extends JpaRepository<Route, Long> {
    // Możesz dodać tu własne metody, np:
    // List<Route> findByStartLocation(String startLocation);
}
