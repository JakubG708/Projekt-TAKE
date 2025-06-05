package com.example.demo.DTOs;

import java.time.LocalTime;

import com.example.demo.models.Route;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
public class RouteDTO {
    private Integer routeId;
    @Schema(type = "string", example = "03:20:15")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime estimatedTime;

    public RouteDTO(Route route) {
        this.routeId = route.getRouteId();
        this.estimatedTime = route.getEstimatedTime();
    }
}
