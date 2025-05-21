package com.example.demo.DTOs;



import org.springframework.hateoas.RepresentationModel;

import com.example.demo.models.Package_;

import lombok.*;

import com.example.demo.controlers.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;


@Getter
@Setter
public class PackageDTO extends RepresentationModel<PackageDTO> {
    private Integer packageId;
    private String status;


    public PackageDTO(Package_ package_) {
        this.packageId = package_.getPackageId();
        this.status = package_.getStatus();


        // Dodaj link do konkretnej paczki (np. PackageController)
        this.add(linkTo(methodOn(PackageController.class)
                .getPackageById(packageId)).withSelfRel());
    }
}
