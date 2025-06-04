package pl.polsl.lab1.gruszka.wiktor.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.polsl.lab1.gruszka.wiktor.models.Segment;

public interface SegmentRepository extends JpaRepository<Segment, Long> {
    // Możesz dodać własne metody, jeśli będziesz potrzebować np. wyszukiwania po relacjach
}
