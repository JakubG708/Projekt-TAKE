package com.example.demo.DTOs;

import com.example.demo.models.RouteList;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

@Getter
public class RouteListDTO extends RepresentationModel<RouteListDTO> {
    private Integer routeListId;
    private String startPoint;
    private String destinationPoint;
    private Double distance;

    public RouteListDTO(RouteList rl) {
        this.routeListId = rl.getRouteListId();
        this.startPoint = rl.getStartPoint();
        this.destinationPoint = rl.getDestinationPoint();
        this.distance = rl.getDistance();
    }
}
