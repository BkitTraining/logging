package com.example.demo.logging.repository;

import com.example.demo.logging.annotation.LogMethod;
import com.example.demo.logging.model.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@LogMethod
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {

}
