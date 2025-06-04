package com.example.demo.DTOs;




import com.example.demo.models.Car;

import lombok.*;




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
