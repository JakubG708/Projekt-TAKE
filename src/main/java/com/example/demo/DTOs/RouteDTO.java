package com.example.demo.DTOs;

import java.time.LocalTime;

import com.example.demo.models.Route;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
public class RouteDTO extends RepresentationModel<RouteDTO> {
    private Integer routeId;

    @Schema(type = "string", example = "03:20:15")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime estimatedTime;

    private Integer carId;
    private Integer routeListId;

    public RouteDTO(Route route) {
        this.routeId = route.getRouteId();
        this.estimatedTime = route.getEstimatedTime();

        this.carId = (route.getCar() != null && route.getCar().getCarId() != null)
                ? route.getCar().getCarId()
                : null;

        this.routeListId = (route.getRouteList() != null && route.getRouteList().getRouteListId() != null)
                ? route.getRouteList().getRouteListId()
                : null;
    }
}