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

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private MonitorRepository monitorRepository;

    @Scheduled(fixedRate = 3600000) // 每小时运行一次
    public void fetchAndStoreMonitorData() throws JsonProcessingException {
        String username = "dublincityapi";
        String password = "Xpa5vAQ9ki";
        String url = "https://data.smartdublin.ie/sonitus-api/api/monitor/10.1.1.1?username=" + username + "&password=" + password;


        ResponseEntity<String> response = restTemplate.postForEntity(url, null, String.class);
        String responseBody = response.getBody();
        System.out.println("API 响应: " + response);

        ObjectMapper mapper = new ObjectMapper();
        Monitor monitor = mapper.readValue(responseBody, Monitor.class);
        monitorRepository.save(monitor); // 将 Monitor 对象保存到数据库中


    }


}
