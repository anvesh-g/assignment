package com.corptec.assignment.batchJob;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

public class EmployeeFieldSetMapper implements FieldSetMapper<EmployeeLineMapper> {

    @Override
    public EmployeeLineMapper mapFieldSet(FieldSet fieldSet) throws BindException {
        return EmployeeLineMapper.builder()
                .firstName(fieldSet.readString("firstName"))
                .lastName(fieldSet.readString("lastName"))
                .gender(fieldSet.readString("gender"))
                .emailId(fieldSet.readString("email"))
                .dateOfBirth(fieldSet.readDate("dateOfBirth","MM/dd/yy"))
                .dateOfJoining(fieldSet.readDate("dateOfJoining", "MM/dd/yy"))
                .salary(fieldSet.readLong("salary"))
                .build();
    }
}
