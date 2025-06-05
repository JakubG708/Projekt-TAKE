package com.example.demo.DTOs;

import java.time.LocalTime;

import com.example.demo.models.Route;

import lombok.*;

@Getter
@Setter
public class RouteDTO {
	private Integer id;
    private String estimatedTime;
    private Integer carId;
    private Integer routeListId;

    public RouteDTO(Route route) {
        this.id = route.getRouteId();
        this.estimatedTime = route.getEstimatedTime() != null ? route.getEstimatedTime().toString() : null;
        
        // zabezpieczenie przed null
        this.carId = (route.getCar() != null && route.getCar().getCarId() != null)
            ? route.getCar().getCarId()
            : null;

        this.routeListId = (route.getRouteList() != null && route.getRouteList().getRouteListId() != null)
            ? route.getRouteList().getRouteListId()
            : null;
    }
}
