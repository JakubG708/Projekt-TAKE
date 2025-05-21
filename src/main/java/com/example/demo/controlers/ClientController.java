package com.example.demo.controlers;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
	    Optional<Client> clientOptional = clientRepo.findById(id);
	    
	    if (clientOptional.isEmpty()) {
	        return ResponseEntity.notFound().build();
	    }
	    
	    Client client = clientOptional.get();
	    List<PackageDTO> packagesDTO = new ArrayList<>();
	    
	    for (Package_ pkg : client.getPackages()) {
	        packagesDTO.add(new PackageDTO(pkg));
	    }
	    
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
	        return ResponseEntity.notFound().build(); // HTTP 404 - Klient nie istnieje
	    }
	    
	    clientRepo.deleteById(id);
	    return ResponseEntity.noContent().build(); // HTTP 204 - Usunięto pomyślnie
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
	
	

}
