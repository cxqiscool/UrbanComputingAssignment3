package tcd.assignment3.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import tcd.assignment3.entity.HourlyAverage;
import tcd.assignment3.entity.Monitor;
import tcd.assignment3.repository.HourlyAverageRepository;
import tcd.assignment3.repository.MonitorRepository;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class SonitusApiService {

    private static final String USERNAME = "dublincityapi";
    private static final String PASSWORD = "Xpa5vAQ9ki";
    private static final String BASE_URL = "https://data.smartdublin.ie/sonitus-api";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private MonitorRepository monitorRepository;

    @Autowired
    private HourlyAverageRepository hourlyAverageRepository;

    @Scheduled(fixedRate = 300000)
    public void fetchAndStoreHourlyAverages() throws JsonProcessingException {
        List<String> serialNumbers = fetchMonitorSerialNumbers();
        System.out.println(serialNumbers);
        if (serialNumbers != null) {
            for (String serialNumber : serialNumbers) {
                fetchAndStoreHourlyAveragesForMonitor(serialNumber);
            }
        }
    }

    private List<String> fetchMonitorSerialNumbers() {
        String monitorsUrl = BASE_URL + "/api/monitors?username=" + USERNAME + "&password=" + PASSWORD;

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(monitorsUrl, null, String.class);
            String responseBody = response.getBody();
            System.out.println("Monitors API response: " + responseBody);

            ObjectMapper mapper = new ObjectMapper();
            JsonNode monitorsArray = mapper.readTree(responseBody);

            List<String> serialNumbers = new ArrayList<>();
            for (JsonNode monitorNode : monitorsArray) {
                serialNumbers.add(monitorNode.get("serial_number").asText());
            }
            return serialNumbers;

        } catch (RestClientException | JsonProcessingException e) {
            System.err.println("error: " + e.getMessage());
            return null;
        }
    }


    private void fetchAndStoreHourlyAveragesForMonitor(String serialNumber) {
        long endTimestamp = Instant.now().getEpochSecond();
        long startTimestamp = Instant.now().minus(5, ChronoUnit.MINUTES).getEpochSecond();
        String hourlyAveragesUrl = BASE_URL + "/api/hourly-averages?username=" + USERNAME + "&password=" + PASSWORD
                + "&monitor=" + serialNumber + "&start=" + startTimestamp + "&end=" + endTimestamp;

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(hourlyAveragesUrl, null, String.class);
            String responseBody = response.getBody();
            System.out.println("Hourly Averages for Monitor " + serialNumber + " API response: " + responseBody);

            ObjectMapper mapper = new ObjectMapper();
            JsonNode hourlyAveragesArray = mapper.readTree(responseBody);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            for (JsonNode dataPoint : hourlyAveragesArray) {
                HourlyAverage hourlyAverage = new HourlyAverage();
                hourlyAverage.setSerialNumber(serialNumber);
                hourlyAverage.setDatetime(LocalDateTime.parse(dataPoint.get("datetime").asText(), formatter));
                hourlyAverage.setLaeq(dataPoint.get("laeq").asDouble());

                // 保存到数据库
                hourlyAverageRepository.save(hourlyAverage);
            }

        } catch (RestClientException | JsonProcessingException e) {
            System.err.println("get monitor " + serialNumber + " error: " + e.getMessage());
        }
    }

}
