package com.example.demo.controlers;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.models.Car;
import com.example.demo.models.Client;
import com.example.demo.repositories.*;




@RestController
@RequestMapping("/client")
public class ClientController {
	
	@Autowired
	ClientRepository clientRepo;
	
	
	@PostMapping("/add")
	public @ResponseBody String addClient(@RequestBody Client client) 
	{
		 clientRepo.save(client);
		 return "Added with id=" + client.getClientId();
	}
	
	@GetMapping("/clients")
	public @ResponseBody Iterable<Client> getClients() 
	{
		return clientRepo.findAll();
	}

}
