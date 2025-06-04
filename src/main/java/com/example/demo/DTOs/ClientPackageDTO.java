package com.example.demo.DTOs;


import org.springframework.hateoas.RepresentationModel;

import com.example.demo.models.Client;

import lombok.*;

import com.example.demo.controller.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;



@NoArgsConstructor
@Getter
@Setter
public class ClientPackageDTO extends RepresentationModel<ClientPackageDTO>{
	
    private String firstName;
    private String lastName;
    
    public ClientPackageDTO(Client client) {
        this.firstName = client.getFirstName();
        this.lastName = client.getLastName();

        this.add(linkTo(methodOn(ClientController.class)
        		.getClientPackages(client.getClientId())).withRel("packages"));
        
    }

}
