package com.example.demo.DTOs;

import jakarta.validation.constraints.*;
import lombok.*;

//ClientRequestDTO.java

@Getter // Lombok - generuje gettery/settery
@Setter
@NoArgsConstructor
public class ClientRequestDTO {
 @NotBlank(message = "Imię jest wymagane")
 private String firstName;

 @NotBlank(message = "Nazwisko jest wymagane")
 private String lastName;

 @NotBlank(message = "Adres jest wymagany")
 private String address;

 @Pattern(regexp = "^\\d{9}$", message = "Niepoprawny format telefonu")
 private String phone;

 @Email(message = "Niepoprawny format email")
 private String email;
}
