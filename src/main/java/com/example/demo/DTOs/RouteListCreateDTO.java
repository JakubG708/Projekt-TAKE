package com.example.demo.DTOs;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RouteListCreateDTO {
    @NotNull(message = "Start point is required")
    private String startPoint;
    @NotNull(message = "Destination Point is required")
    private String destinationPoint;
    @NotNull(message = "Distance ID is required")
    private Double distance;
}
