package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.models.Package_;
import com.example.demo.models.Route;

@Repository
public interface PackageRepository extends CrudRepository<Package_, Integer> {
    List<Package_> findByRoute_Car_CarId(Integer carId);
    List<Package_> findByClient_ClientId(Integer clientId);
    List<Package_> findByRoute_RouteList_RouteListId(Integer routeListId);
    List<Package_> findByRoute_RouteId(Integer routeId);
    List<Package_> findByRoute(Route route);

    @Query("SELECT p FROM Package_ p LEFT JOIN FETCH p.route WHERE p.client.clientId = :clientId")
    List<Package_> findByClientWithRoute(Integer clientId);
}
