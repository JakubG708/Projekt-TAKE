package com.example.demo.controller;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.models.*;
import com.example.demo.repositories.*;

import jakarta.validation.Valid;

import com.example.demo.DTOs.*;




@RestController
@RequestMapping("/client")
public class ClientController {
	
	@Autowired
	ClientRepository clientRepo;
	
	@Autowired
    private PackageRepository packageRepo;
	
	
	@PostMapping("/add")
	public ResponseEntity<ClientDTO> addClient(
		    @Valid @RequestBody ClientRequestDTO clientRequest
		) {
		    // Mapowanie DTO -> Encja
		    Client newClient = new Client();
		    newClient.setFirstName(clientRequest.getFirstName());
		    newClient.setLastName(clientRequest.getLastName());
		    newClient.setAddress(clientRequest.getAddress());
		    newClient.setPhone(clientRequest.getPhone());
		    newClient.setEmail(clientRequest.getEmail());

		    Client savedClient = clientRepo.save(newClient);
		   
		    return ResponseEntity.ok(new ClientDTO(savedClient));
	}
	
	
	
	@GetMapping("/clients")
	public @ResponseBody CollectionModel<ClientDTO> getClients() 
	{
		List<ClientDTO> clientsDTO = new ArrayList<>();
		for(Client client:clientRepo.findAll())
		{
			clientsDTO.add(new ClientDTO(client));
		}
		return CollectionModel.of(clientsDTO);
	}

	@GetMapping("/{id}/packages")
	public ResponseEntity<CollectionModel<PackageDTO>> getClientPackages(@PathVariable Integer id) {
	    List<Package_> packages = packageRepo.findByClientWithRoute(id);
	    
	    List<PackageDTO> packagesDTO = packages.stream()
	                                          .map(PackageDTO::new)
	                                          .collect(Collectors.toList());
	    
	    return ResponseEntity.ok(CollectionModel.of(packagesDTO));
	}
	
	
	@GetMapping("/{id}/contact")
	public @ResponseBody ClientContactInformationDTO getClientContactInformation(@PathVariable Integer id)
	{
		Client c = clientRepo.findById(id).orElse(null);
		return new ClientContactInformationDTO(c);
	}
	
	

	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteClient(@PathVariable Integer id) {
	    Optional<Client> clientOptional = clientRepo.findById(id);
	    
	    if (clientOptional.isEmpty()) {
	        return ResponseEntity.notFound().build();
	    }

	    // Usuń paczki przypisane do klienta
	    List<Package_> packages = packageRepo.findByClient_ClientId(id);
	    if (!packages.isEmpty()) {
	        packageRepo.deleteAll(packages);
	    }

	    // Usuń klienta
	    clientRepo.deleteById(id);
	    
	    return ResponseEntity.noContent().build();
	}

	
	@PutMapping("/update/{id}")
	public ResponseEntity<ClientDTO> updateClient(
	    @PathVariable Integer id,
	    @Valid @RequestBody ClientRequestDTO updateDTO
	) {
	    Optional<Client> clientOptional = clientRepo.findById(id);
	    
	    if (clientOptional.isEmpty()) {
	        return ResponseEntity.notFound().build(); // HTTP 404
	    }

	    Client existingClient = clientOptional.get();
	    
	    existingClient.setFirstName(updateDTO.getFirstName());
	    existingClient.setLastName(updateDTO.getLastName());
	    existingClient.setAddress(updateDTO.getAddress());
	    existingClient.setPhone(updateDTO.getPhone());
	    existingClient.setEmail(updateDTO.getEmail());

	    Client updatedClient = clientRepo.save(existingClient);
	    
	    return ResponseEntity.ok(new ClientDTO(updatedClient)); // HTTP 200
	}
	
	
	@GetMapping("/{id}/delivery-dates")
	public ResponseEntity<List<LocalDate>> getClientDeliveryDates(@PathVariable Integer id) {
	    Optional<Client> clientOptional = clientRepo.findById(id);

	    if (clientOptional.isEmpty()) {
	        return ResponseEntity.notFound().build();
	    }

	    List<Package_> packages = packageRepo.findByClient_ClientId(id);
	    List<LocalDate> deliveryDates = packages.stream()
	            .map(Package_::getDeliveryDate)
	            .collect(Collectors.toList());

	    return ResponseEntity.ok(deliveryDates);
	}
	

}
