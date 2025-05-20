package com.example.demo.repositories;

import com.example.demo.models.Car;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends CrudRepository<Car, Integer> {

	List<Car> findByBrand(String brand);

}
