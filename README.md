**Pre-requisites/Steps to the run job:**
----------------------------------------

**Requires Installed JDK version of 8 or higher**

**Step 1:** Run the attached sql file import.sql in Postgres db.

**Step 2:** Place the attached data file employee-data.csv into s3 bucket or local system depending upon your choice.
    
**Step 3:** Now open terminal pointing to current directory of jar (assignment.jar which is attached) that resides in your system.

    We have to pass following parameters depending up on the location of file

    A) If file is placed in s3 bucket

		--spring.profiles.active=prod
		--spring.datasource.url=jdbc:postgresql://localhost:5432/demo (place your db url)
		--spring.datasource.username=postgres (place your db username)
		--spring.datasource.password=12345 (place your db password)
		--resource.folder=/Users/anveshgatadi/Data/code/java/assignment/src/main/resources/data (place path of the directory where you want to download files from s3)
		--aws-s3.bucket-name=corptec-files (your s3 bucket name)
		--aws-s3.access-key=AKIA2KWVZLJLYGVDF2XE  (your IAM user access-key)
		--aws-s3.secret-key=uy1x4+3yfrnmlzJKMzwySPjNWsCC1rPxplwfz9bB (your IAM user secret-key)
    
    Command to run the jar:
    
    java -jar assignment.jar --spring.profiles.active=prod --spring.datasource.url=jdbc:postgresql://localhost:5432/demo --spring.datasource.username=postgres --spring.datasource.password=12345 --resource.folder=/Users/anveshgatadi/Desktop/test/files --aws-s3.bucket-name=corptec-files --aws-s3.access-key=AKIA2KWVZLJLYGVDF2XE --aws-s3.secret-key=uy1x4+3yfrnmlzJKMzwySPjNWsCC1rPxplwfz9bB

    B) If file is placed in your local system
            --spring.profiles.active=dev
		--spring.datasource.url=jdbc:postgresql://localhost:5432/demo (place your db url)
		--spring.datasource.username=postgres (place your db username)
		--spring.datasource.password=12345 (place your db password)
		--resource.folder=/Users/anveshgatadi/Data/code/java/assignment/src/main/resources/data (place path of the directory where placed employee-data.csv)
    
    Command to run the jar:
    
    java -jar assignment.jar --spring.profiles.active=dev --spring.datasource.url=jdbc:postgresql://localhost:5432/demo --spring.datasource.username=postgres --spring.datasource.password=12345 --resource.folder=/Users/anveshgatadi/Desktop/test/files

**Step 4:** Once the execution is complete, do not close the terminal and you would see records inserted into Employee Table
        
        Query: select * from Employee;

**Step 5:** Application(jar) will open two end points for access data
        

    First end Point: http://localhost:8080/employee/all

        Which would return all employess inserted into db

	Second end Point: http://localhost:8080/employee/{emailType}

	Example: 
		http://localhost:8080/employee/gmail  ==> which would return employees with gmail account
		http://localhost:8080/employee/hotmail ==>  which would return employees with hotmail account


