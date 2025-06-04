package com.example.demo.models;

import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Route {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer routeId;

    @ManyToOne
    @JoinColumn(name = "car_id", nullable = false)
    private Car car;

    @ManyToOne
    @JoinColumn(name = "route_list_id", nullable = true)
    private RouteList routeList;

    @Column(nullable = false)
    private LocalTime estimatedTime;

}
