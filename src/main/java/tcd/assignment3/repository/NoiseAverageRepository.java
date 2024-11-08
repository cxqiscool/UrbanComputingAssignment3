package tcd.assignment3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tcd.assignment3.entity.NoiseAverage;

public interface NoiseAverageRepository extends JpaRepository<NoiseAverage, Long> {}
