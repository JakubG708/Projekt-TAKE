package com.example.demo.DTOs;

import com.example.demo.models.Package_;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;

@Getter
@Setter
public class PackageForRouteDTO extends RepresentationModel<PackageForRouteDTO> {
	private Integer id;
    private String status;
    private LocalDate sentDate;
    private LocalDate deliveryDate;

    public PackageForRouteDTO(Package_ p) {
        this.id = p.getPackageId();
        this.status = p.getStatus();
        this.sentDate = p.getSentDate();
        this.deliveryDate = p.getDeliveryDate();
    }

}
