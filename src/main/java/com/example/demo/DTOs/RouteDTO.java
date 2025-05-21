package com.example.demo.DTOs;

import java.time.LocalTime;

import com.example.demo.models.Route;

import lombok.*;

@Getter
@Setter
public class RouteDTO {
    private Integer routeId;
    private LocalTime estimatedTime;

    public RouteDTO(Route route) {
        this.routeId = route.getRouteId();
        this.estimatedTime = route.getEstimatedTime();
    }
}
