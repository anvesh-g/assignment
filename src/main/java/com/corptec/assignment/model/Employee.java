package com.corptec.assignment.model;

import lombok.Builder;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Employee  {
    Integer employeeId;
    String firstName;
    String lastName;
    String gender;
    String emailId;
    Date dateOfBirth;
    Date dateOfJoining;
    Long salary;
}
