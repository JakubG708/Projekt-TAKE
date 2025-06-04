package com.example.demo.DTOs;



import lombok.*;


import com.example.demo.models.*;

@Getter
@Setter
@NoArgsConstructor
public class ClientContactInformationDTO {
	
	private String address;
    private String phone;
    private String email;
    
    public ClientContactInformationDTO(Client c)
    {
    	this.address = c.getAddress();
    	this.phone = c.getPhone();
    	this.email = c.getEmail();
    }

}
