package tcd.assignment3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tcd.assignment3.entity.HourlyAverage;

public interface HourlyAverageRepository extends JpaRepository<HourlyAverage, Long> {}
