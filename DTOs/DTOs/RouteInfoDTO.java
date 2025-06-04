package pl.polsl.lab1.gruszka.wiktor.DTOs;

public class RouteInfoDTO {
    private Long id;
    private int estimatedTime;
    private String carModel;
    private String startLocation;
    private String endLocation;

    public RouteInfoDTO() {}

    public RouteInfoDTO(Long id, int estimatedTime, String carModel, String startLocation, String endLocation) {
        this.id = id;
        this.estimatedTime = estimatedTime;
        this.carModel = carModel;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public int getEstimatedTime() { return estimatedTime; }
    public void setEstimatedTime(int estimatedTime) { this.estimatedTime = estimatedTime; }

    public String getCarModel() { return carModel; }
    public void setCarModel(String carModel) { this.carModel = carModel; }

    public String getStartLocation() { return startLocation; }
    public void setStartLocation(String startLocation) { this.startLocation = startLocation; }

    public String getEndLocation() { return endLocation; }
    public void setEndLocation(String endLocation) { this.endLocation = endLocation; }
} 
