package dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PackageCreateDTO {
    private Integer clientId;
    private Integer routeId;
    private String status;
    private LocalDate sentDate;
    private LocalDate deliveryDate;
}
