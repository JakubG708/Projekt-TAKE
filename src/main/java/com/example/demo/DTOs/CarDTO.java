package com.example.demo.DTOs;


import org.springframework.hateoas.RepresentationModel;

import com.example.demo.models.Car;

import lombok.*;

import com.example.demo.controlers.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;



@NoArgsConstructor
@Getter
@Setter
public class CarDTO {
	private String brand;
	
	
	
	public CarDTO(Car c)
	{
		this.brand = c.getBrand();
	}

}
