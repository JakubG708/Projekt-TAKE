package com.example.demo.DTOs;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
public class RouteRequestDTO {

    @NotNull(message = "Car ID is required")
    private Integer carId;

    private Integer routeListId;

    @NotNull(message = "Estimated time is required")
    @Schema(type = "string", example = "03:20:15")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime estimatedTime;
}
