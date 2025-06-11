package com.example.demo.DTOs;
import com.example.demo.models.Car;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;


@NoArgsConstructor
@Getter
@Setter
public class CarRequestDTO extends RepresentationModel<CarRequestDTO>
{
	private String brand;
	private Integer carId;

	public CarRequestDTO(Car c)
	{
		this.carId = c.getCarId();
		this.brand = c.getBrand();
	}

}
