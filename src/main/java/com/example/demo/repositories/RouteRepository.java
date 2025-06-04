package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.models.Route;

@Repository
public interface RouteRepository extends CrudRepository<Route, Integer> {
    List<Route> findByCar_CarId(Integer carId);
}
