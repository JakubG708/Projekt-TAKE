package com.example.demo.DTOs;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
public class RouteCreateDTO {
    private Integer carId;
    private Integer routeListId;
    private LocalTime estimatedTime;
}
