package com.example.demo.DTOs;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
public class RouteCreateDTO {
    @NotNull(message = "Car Id time is required")
    private Integer carId;
    @NotNull(message = "Route List Id time is required")
    private Integer routeListId;
    @NotNull(message = "Estimated Time time is required")
    @Schema(type = "string", example = "03:20:15")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime estimatedTime;
}
