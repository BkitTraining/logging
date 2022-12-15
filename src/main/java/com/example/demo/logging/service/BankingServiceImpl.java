package com.example.demo.logging.service;

import com.example.demo.logging.annotation.LogMethod;
import com.example.demo.logging.model.dto.BankInfo;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
@LogMethod
@Log4j2
public class BankingServiceImpl implements BankingService {

  private final RestTemplate restTemplate;

  @Override
  public BankInfo getBankInfo(Long employeeId) {
    return restTemplate.getForObject("http://localhost:8081/micro2/banking/" + employeeId, BankInfo.class);
  }

}
