package com.example.demo.DTOs;

import com.example.demo.models.Package_;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PackageResponseDTO {
    private Integer id;
    private String status;
    private LocalDate sentDate;
    private LocalDate deliveryDate;
    private String clientName;
    private String routeInfo;

    public PackageResponseDTO(Package_ pack) {
        this.id = pack.getPackageId();
        this.status = pack.getStatus();
        this.sentDate = pack.getSentDate();
        this.deliveryDate = pack.getDeliveryDate();
        this.clientName = pack.getClient().getFirstName() + " " + pack.getClient().getLastName();
        this.routeInfo = pack.getRoute().getRouteList().getStartPoint() + " to " +
                pack.getRoute().getRouteList().getDestinationPoint();
    }
}
