package com.example.demo.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Car {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer carId;

    @Column(nullable = false)
    private String brand;


    
    
}
