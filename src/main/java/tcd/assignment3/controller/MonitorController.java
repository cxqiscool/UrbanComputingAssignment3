package tcd.assignment3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tcd.assignment3.entity.HourlyAverage;
import tcd.assignment3.entity.Monitor;
import tcd.assignment3.repository.HourlyAverageRepository;
import tcd.assignment3.repository.MonitorRepository;

import java.util.List;

@RestController
@RequestMapping("/api/monitors")
public class MonitorController {

    @Autowired
    private MonitorRepository monitorRepository;

    @Autowired
    private HourlyAverageRepository hourlyAverageRepository;

    @GetMapping
    public List<Monitor> getAllMonitors() {
        return monitorRepository.findAll();
    }

    @GetMapping("/{serialNumber}/data")
    public List<HourlyAverage> getSensorData(@PathVariable String serialNumber) {
        return hourlyAverageRepository.findBySerialNumber(serialNumber);
    }
}
