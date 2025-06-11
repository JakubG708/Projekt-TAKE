package com.example.demo.DTOs;

import com.example.demo.models.Package_;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;

@Getter
@Setter
public class PackageResponseDTO extends RepresentationModel<PackageResponseDTO> {
    private Integer packageId;
    private String status;
    private LocalDate sentDate;
    private LocalDate deliveryDate;
    private String clientName;
    private String routeInfo;

    public PackageResponseDTO(Package_ pack) {
        this.packageId = pack.getPackageId();
        this.status = pack.getStatus();
        this.sentDate = pack.getSentDate();
        this.deliveryDate = pack.getDeliveryDate();
        this.clientName = pack.getClient().getFirstName() + " " + pack.getClient().getLastName();
        this.routeInfo = pack.getRoute().getRouteList().getStartPoint() + " to " +
                pack.getRoute().getRouteList().getDestinationPoint();
    }
}
