package com.example.demo.repositories;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.models.Client;

@Repository
public interface ClientRepository extends CrudRepository<Client, Integer>{
	

}
