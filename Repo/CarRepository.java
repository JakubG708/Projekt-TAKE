package pl.polsl.lab1.gruszka.wiktor.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.polsl.lab1.gruszka.wiktor.models.Car;

public interface CarRepository extends JpaRepository<Car, Long> {
}
