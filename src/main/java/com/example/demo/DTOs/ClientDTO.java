package com.example.demo.DTOs;


import com.example.demo.models.Client;

import lombok.*;




@NoArgsConstructor
@Getter
@Setter
public class ClientDTO {
    private Integer id;
    private String firstName;
    private String lastName;
    private String address;
    private String phone;
    private String email;

    public ClientDTO(Client client) {
        this.id = client.getClientId();
        this.firstName = client.getFirstName();
        this.lastName = client.getLastName();
        this.address = client.getAddress();
        this.phone = client.getPhone();
        this.email = client.getEmail();
        
    }
}
