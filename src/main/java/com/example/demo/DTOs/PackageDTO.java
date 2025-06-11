package com.example.demo.DTOs;



import org.springframework.hateoas.RepresentationModel;

import com.example.demo.models.Package_;

import lombok.*;

import com.example.demo.controller.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.time.LocalDate;
import java.time.LocalTime;


@Getter
@Setter
public class PackageDTO extends RepresentationModel<PackageDTO> {
    private Integer packageId;
    private String status;
    private LocalDate sentDate;
    private LocalDate deliveryDate;
    private LocalTime estimatedTime;

    public PackageDTO(Package_ package_) {
        this.packageId = package_.getPackageId();
        this.status = package_.getStatus();
        this.sentDate = package_.getSentDate();
        this.deliveryDate = package_.getDeliveryDate();

        if (package_.getRoute() != null) {
            this.estimatedTime = package_.getRoute().getEstimatedTime();
        }

    }
}

