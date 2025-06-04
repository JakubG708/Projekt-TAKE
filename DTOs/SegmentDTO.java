package pl.polsl.lab1.gruszka.wiktor.DTOs;

public class SegmentDTO {
    private Long id;
    private String start;
    private String end;

    public SegmentDTO() {}

    public SegmentDTO(Long id, String start, String end) {
        this.id = id;
        this.start = start;
        this.end = end;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getStart() { return start; }
    public void setStart(String start) { this.start = start; }

    public String getEnd() { return end; }
    public void setEnd(String end) { this.end = end; }
}
