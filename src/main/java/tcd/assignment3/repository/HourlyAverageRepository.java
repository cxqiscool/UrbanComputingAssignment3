package tcd.assignment3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tcd.assignment3.entity.HourlyAverage;

import java.util.List;

public interface HourlyAverageRepository extends JpaRepository<HourlyAverage, Long> {
    List<HourlyAverage> findBySerialNumber(String serialNumber);
}
