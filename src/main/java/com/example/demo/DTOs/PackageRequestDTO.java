package com.example.demo.DTOs;



import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;


import java.time.LocalDate;





@Getter
@Setter
public class PackageRequestDTO {
    @NotNull(message = "Client ID is required")
    private Integer clientId;

    @NotNull(message = "Route ID is required")
    private Integer routeId;

    @NotNull(message = "Car ID is required")
    private Integer carId;

    @NotBlank(message = "Status is required")
    private String status;

    @NotNull(message = "Sent date is required")
    private LocalDate sentDate;

    private LocalDate deliveryDate;
}
