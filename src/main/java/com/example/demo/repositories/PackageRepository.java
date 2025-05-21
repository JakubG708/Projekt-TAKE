package com.example.demo.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.models.Package_;
import com.example.demo.models.Route;

@Repository
public interface PackageRepository extends CrudRepository<Package_, Integer>{
	List<Package_> findByCar_CarId(Integer carId);
	List<Package_> findByClient_ClientId(Integer clientId);
	 @Query("SELECT p FROM Package_ p LEFT JOIN FETCH p.route WHERE p.client.clientId = :clientId")
	    List<Package_> findByClientWithRoute(Integer clientId);

}
