package pl.polsl.lab1.gruszka.wiktor.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.polsl.lab1.gruszka.wiktor.models.Package;

public interface PackageRepository extends JpaRepository<Package, Long> {
}
