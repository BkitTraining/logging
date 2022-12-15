package com.example.demo.logging.service;

import com.example.demo.logging.exception.ResourceNotFoundException;
import com.example.demo.logging.model.EmployeeEntity;
import com.example.demo.logging.model.dto.BankInfo;

import java.util.List;

public interface EmployeeService {
  List<EmployeeEntity> getAllEmployees();

  EmployeeEntity getEmployeeById(Long employeeId) throws ResourceNotFoundException;

  EmployeeEntity addEmployee(EmployeeEntity employeeEntity);

  EmployeeEntity updateEmployee(Long employeeId, EmployeeEntity employeeEntityDetails) throws ResourceNotFoundException;

  void deleteEmployee(Long employeeId) throws ResourceNotFoundException;

  BankInfo getBankInfo(Long employeeId) throws ResourceNotFoundException;
}
