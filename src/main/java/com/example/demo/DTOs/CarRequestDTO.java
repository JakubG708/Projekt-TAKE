package com.example.demo.DTOs;
import com.example.demo.models.Car;

import lombok.*;



@NoArgsConstructor
@Getter
@Setter
public class CarRequestDTO 
{
	
	private String brand;
	private Integer carId;
	
	
	
	public CarRequestDTO(Car c)
	{
		this.carId = c.getCarId();
		this.brand = c.getBrand();
	}
	

}
