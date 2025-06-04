package com.example.demo.DTOs;
import org.springframework.hateoas.RepresentationModel;

import com.example.demo.models.Car;

import lombok.*;

import com.example.demo.controller.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;



@NoArgsConstructor
@Getter
@Setter
public class CarPackageDTO extends RepresentationModel<CarPackageDTO>{
	private String brand;
	
	public CarPackageDTO(Car c)
	{
		this.brand = c.getBrand();
		this.add(linkTo(methodOn(CarController.class)
        		.getCarPackages(c.getCarId())).withRel("packages"));
	}


}
