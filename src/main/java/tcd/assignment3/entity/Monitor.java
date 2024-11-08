package tcd.assignment3.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
public class Monitor {
    @Id
    @Getter
    @Setter
    @JsonProperty("serial_number")
    private String serialNumber;
    @JsonProperty("label")
    private String label;
    private String location;
    private BigDecimal latitude;
    private BigDecimal longitude;
    @JsonProperty("last_calibrated")
    private String lastCalibrated;
}
