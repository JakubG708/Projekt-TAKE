package com.example.demo.DTOs;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class PackageDeliveryDateDTO {
    private Integer packageId;
    private LocalDate deliveryDate;

    public PackageDeliveryDateDTO(Integer packageId, LocalDate deliveryDate) {
        this.packageId = packageId;
        this.deliveryDate = deliveryDate;
    }
}

