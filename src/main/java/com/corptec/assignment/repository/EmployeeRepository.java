package com.corptec.assignment.repository;

import com.corptec.assignment.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {

    @Query("FROM Employee e where e.emailId like %:emailType%")
    List<Employee> findByEmailType(@Param("emailType") String emailType);
}
