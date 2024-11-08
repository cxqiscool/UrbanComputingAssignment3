package tcd.assignment3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tcd.assignment3.entity.Monitor;

public interface MonitorRepository extends JpaRepository<Monitor, String> {}
