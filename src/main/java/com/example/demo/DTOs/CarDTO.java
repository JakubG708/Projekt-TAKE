package com.example.demo.DTOs;




import com.example.demo.models.Car;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;


@NoArgsConstructor
@Getter
@Setter
public class CarDTO extends RepresentationModel<CarDTO> {
	private String brand;
	
	
	
	public CarDTO(Car c)
	{
		this.brand = c.getBrand();
	}

}
