package tcd.assignment3.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import tcd.assignment3.entity.Monitor;
import tcd.assignment3.repository.MonitorRepository;

@Service
public class SonitusApiService {

    private static final String USERNAME = "dublincityapi";
    private static final String PASSWORD = "Xpa5vAQ9ki";
    private static final String BASE_URL = "https://data.smartdublin.ie/sonitus-api";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private MonitorRepository monitorRepository;

    @Scheduled(fixedRate = 3600000) // 每小时运行一次
    public void fetchAndStoreAllMonitorData() throws JsonProcessingException {
        String monitorsUrl = BASE_URL + "/api/monitors?username=" + USERNAME + "&password=" + PASSWORD;

        try {
            // 获取所有监测器的列表
            ResponseEntity<String> response = restTemplate.postForEntity(monitorsUrl, null, String.class);
            String responseBody = response.getBody();
            System.out.println("Monitors API 响应: " + responseBody);

            ObjectMapper mapper = new ObjectMapper();
            JsonNode monitorsArray = mapper.readTree(responseBody);

            // 遍历每个监测器，获取详细信息并保存
            for (JsonNode monitorNode : monitorsArray) {
                String serialNumber = monitorNode.get("serial_number").asText();
                fetchAndStoreMonitorDetails(serialNumber);
            }

        } catch (RestClientException e) {
            System.err.println("获取监测器列表时出错: " + e.getMessage());
        }
    }

    private void fetchAndStoreMonitorDetails(String serialNumber) throws JsonProcessingException {
        String monitorUrl = BASE_URL + "/api/monitor/" + serialNumber + "?username=" + USERNAME + "&password=" + PASSWORD;

        try {
            // 构造 POST 请求以获取单个监测器的详细信息
            ResponseEntity<String> response = restTemplate.postForEntity(monitorUrl, null, String.class);
            String responseBody = response.getBody();
            System.out.println("Monitor " + serialNumber + " API 响应: " + responseBody);

            // 将响应转换为 Monitor 实体并保存
            ObjectMapper mapper = new ObjectMapper();
            Monitor monitor = mapper.readValue(responseBody, Monitor.class);
            monitorRepository.save(monitor);

        } catch (RestClientException e) {
            System.err.println("获取监测器 " + serialNumber + " 详细信息时出错: " + e.getMessage());
        }
    }
}
