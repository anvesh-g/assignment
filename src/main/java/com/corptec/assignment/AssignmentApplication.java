package com.corptec.assignment;

import org.apache.catalina.core.ApplicationContext;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.UUID;

import static com.corptec.assignment.batchJob.Constants.*;

@EnableBatchProcessing
@SpringBootApplication
public class AssignmentApplication {

	@Autowired
	private Job job;

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(AssignmentApplication.class, args);
		try{
			JobLauncher jobLauncher = 	context.getBean(JobLauncher.class);
			Job job = context.getBean(JOB_NAME, Job.class);
			JobParameters jobParameters = new JobParametersBuilder()
					.addLong(JOB_ID, System.currentTimeMillis())
					.addLong(JOB_START_TIME, System.currentTimeMillis())
					.toJobParameters();

			JobExecution execution = jobLauncher.run(job, jobParameters);
			System.out.println("STATUS :: "+execution.getStatus());
		}catch (Exception e) {
			e.printStackTrace();
		}

	}

}
