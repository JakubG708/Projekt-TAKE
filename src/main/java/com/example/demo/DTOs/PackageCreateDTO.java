package com.example.demo.DTOs;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PackageCreateDTO {
    @NotNull(message = "Client Id time is required")
    private Integer clientId;
    @NotNull(message = "Route Id Id time is required")
    private Integer routeId;
    @NotNull(message = "Status time is required")
    private String status;
    @NotNull(message = "Sent Date time is required")
    private LocalDate sentDate;
    private LocalDate deliveryDate;
}
