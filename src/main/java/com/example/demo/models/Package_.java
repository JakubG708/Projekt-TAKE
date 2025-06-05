package com.example.demo.models;


import java.time.LocalDate;


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
public class Package_ {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer packageId;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @ManyToOne
    @JoinColumn(name = "route_id", nullable = true)
    private Route route;

    @Column(nullable = false, length = 50)
    private String status;

    @Column(nullable = false)
    private LocalDate sentDate;

    private LocalDate deliveryDate;

}
