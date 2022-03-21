package com.example.demoApp.controller;

import com.example.demoApp.dto.EmployeeDTO;
import com.example.demoApp.dto.EmployeeSearchCriteria;
import com.example.demoApp.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

   @PostMapping("/search")
    public EmployeeDTO search(@RequestBody  EmployeeSearchCriteria searchCriteria){
        System.out.println("I am in search controller for employee");
       return employeeService.findUsingBean(searchCriteria);
    }
/*
    @GetMapping("/find")
    public Employee search(@RequestParam("name") String name){
        System.out.println("I am in search controller for employee");
        return employeeRepository.findByName(name);
    }*/

    @PostMapping("/add")
    public EmployeeDTO add(@RequestBody EmployeeDTO employee){
        return employeeService.add(employee);
    }

}
