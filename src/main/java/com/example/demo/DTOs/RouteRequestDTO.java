package com.example.demo.DTOs;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
public class RouteRequestDTO {

    @NotNull(message = "Car ID is required")
    private Integer carId;

    private Integer routeListId; // może być null – jak w encji Route

    @NotNull(message = "Estimated time is required")
    private LocalTime estimatedTime;
}
