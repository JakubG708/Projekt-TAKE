package pl.polsl.lab1.gruszka.wiktor.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.polsl.lab1.gruszka.wiktor.DTOs.SegmentDTO;
import pl.polsl.lab1.gruszka.wiktor.DTOs.RouteDTO;
import pl.polsl.lab1.gruszka.wiktor.DTOs.PackageDTO;
import pl.polsl.lab1.gruszka.wiktor.models.Segment;
import pl.polsl.lab1.gruszka.wiktor.repositories.SegmentRepository;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/segments")
public class SegmentController {

    @Autowired
    private SegmentRepository segmentRepository;

    // 31. Info o odcinku
    @GetMapping("/{id}")
    public SegmentDTO getSegment(@PathVariable Long id) {
        Segment segment = segmentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Segment not found"));
        return new SegmentDTO(segment.getId(), segment.getStart(), segment.getEnd());
    }

    // 32. Wszystkie trasy korzystające z odcinka
    @GetMapping("/{id}/routes")
    public List<RouteDTO> getRoutesForSegment(@PathVariable Long id) {
        Segment segment = segmentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Segment not found"));
        return segment.getRoutes().stream()
            .map(route -> new RouteDTO(route.getId(), route.getStartLocation(), route.getEndLocation()))
            .collect(Collectors.toList());
    }

    // 33. Wszystkie paczki przejeżdżające przez odcinek
    @GetMapping("/{id}/packages")
    public List<PackageDTO> getPackagesForSegment(@PathVariable Long id) {
        Segment segment = segmentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Segment not found"));
        return segment.getPackages().stream()
            .map(pkg -> new PackageDTO(pkg.getId(), pkg.getWeight(), pkg.getStatus()))
            .collect(Collectors.toList());
    }
}
