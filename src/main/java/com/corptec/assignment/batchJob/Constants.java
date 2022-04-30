package com.corptec.assignment.batchJob;

public class Constants {

    public static final String JOB_ID = "jobId";

    public static final String READ_AND_WRITE_FILE = "readAndWriteFileStep";

    public static final String JOB_START_TIME = "jobStartTime";

    public static final String JOB_NAME = "employeeRecordFeed";

    public static final String[] EMPLOYEE_HEADERS = {"firstName", "lastName", "gender", "email", "dateOfBirth", "dateOfJoining", "salary"};

    public static final String INSERT_QUERY = "INSERT INTO EMPLOYEE(first_name, last_name, gender, email_id, date_of_birth, date_of_joining, salary) VALUES (:firstName, :lastName, :gender, :emailId, :dateOfBirth, :dateOfJoining, :salary)";
}
