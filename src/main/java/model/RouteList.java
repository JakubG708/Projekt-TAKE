package model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class RouteList {
	
	 @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer routeListId;

    @Column(nullable = false)
    private String startPoint;

    @Column(nullable = false)
    private String destinationPoint;

    @Column(nullable = false)
    private Double distance;

}
