package tcd.assignment3.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class HourlyAverage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String serialNumber;

    private LocalDateTime datetime;

    private double laeq;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public LocalDateTime getDatetime() {
        return datetime;
    }

    public void setDatetime(LocalDateTime datetime) {
        this.datetime = datetime;
    }

    public double getLaeq() {
        return laeq;
    }

    public void setLaeq(double laeq) {
        this.laeq = laeq;
    }
}
