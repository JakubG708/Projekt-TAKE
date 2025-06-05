package com.example.demo.repositories;

import com.example.demo.models.RouteList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RouteListRepository extends JpaRepository<RouteList, Integer> {
}
