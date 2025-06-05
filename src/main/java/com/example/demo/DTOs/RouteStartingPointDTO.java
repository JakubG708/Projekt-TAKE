package com.example.demo.DTOs;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RouteStartingPointDTO {
    private Integer routeId;
    private String startPoint;

    public RouteStartingPointDTO(Integer routeId, String startPoint) {
        this.routeId = routeId;
        this.startPoint = startPoint;
    }

}
