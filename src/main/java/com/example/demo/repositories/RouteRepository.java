package com.example.demo.repositories;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.models.Route;

public interface RouteRepository extends CrudRepository<Route, Integer>{

}
