package com.corptec.assignment.service;

import com.corptec.assignment.model.Employee;
import com.corptec.assignment.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    public EmployeeRepository employeeRepository;

    public List<Employee> getAllEmployees() {
        List<com.corptec.assignment.entity.Employee> employees = this.employeeRepository.findAll();
        return getEmployeeModel(employees);
    }

    public List<Employee> getEmployeesByEmailType(String emailType) {
        List<com.corptec.assignment.entity.Employee> employees = this.employeeRepository.findByEmailType("@"+emailType);
        return getEmployeeModel(employees);
    }

    public List<Employee> getEmployeeModel(List<com.corptec.assignment.entity.Employee> employees) {
        List<Employee> employeeList = employees.stream().map(employee -> {
            return Employee.builder()
                    .employeeId(employee.getEmployeeId())
                    .firstName(employee.getFirstName())
                    .lastName(employee.getLastName())
                    .gender(employee.getGender())
                    .emailId(employee.getEmailId())
                    .dateOfBirth(employee.getDateOfBirth())
                    .dateOfJoining(employee.getDateOfJoining())
                    .salary(employee.getSalary())
                    .build();
        }).collect(Collectors.toList());
        return employeeList;
    }

}
