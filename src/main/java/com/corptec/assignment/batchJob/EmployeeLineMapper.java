package com.corptec.assignment.batchJob;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeeLineMapper {
    String firstName;
    String lastName;
    String gender;
    String emailId;
    Date dateOfBirth;
    Date dateOfJoining;
    Long salary;
}
