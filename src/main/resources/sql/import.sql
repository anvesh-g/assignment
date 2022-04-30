CREATE TABLE Employee (
    employee_id int NOT NULL GENERATED ALWAYS AS IDENTITY,
    first_name varchar(255),
    last_name varchar(255),
    gender char(1),
	email_id varchar(255),
	date_of_birth date,
	date_of_joining date,
    salary int
);