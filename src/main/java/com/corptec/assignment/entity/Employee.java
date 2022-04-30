package com.corptec.assignment.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.Date;

@Builder
@Entity
@Data
@Table(name="Employee")
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="employee_id")
    Integer employeeId;
    @Column(name="first_name")
    String firstName;
    @Column(name="last_name")
    String lastName;
    @Column(name="gender")
    String gender;
    @Column(name="email_id")
    String emailId;
    @Column(name="date_of_birth")
    Date dateOfBirth;
    @Column(name="date_of_joining")
    Date dateOfJoining;
    @Column(name="salary")
    Long salary;
}
