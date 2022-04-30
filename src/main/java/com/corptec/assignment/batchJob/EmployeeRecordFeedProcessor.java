package com.corptec.assignment.batchJob;

import org.springframework.batch.item.ItemProcessor;


public class EmployeeRecordFeedProcessor implements ItemProcessor<EmployeeLineMapper, EmployeeLineMapper> {

    @Override
    public EmployeeLineMapper process(EmployeeLineMapper employee) {
        return EmployeeLineMapper.builder()
                .firstName(employee.getFirstName().toUpperCase())
                .lastName(employee.getLastName().toUpperCase())
                .gender(employee.getGender())
                .emailId(employee.getEmailId())
                .dateOfBirth(employee.getDateOfBirth())
                .dateOfJoining(employee.getDateOfJoining())
                .salary(employee.getSalary())
                .build();
    }
}
