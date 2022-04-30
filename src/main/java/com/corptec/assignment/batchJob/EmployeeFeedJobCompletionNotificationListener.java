package com.corptec.assignment.batchJob;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static com.corptec.assignment.batchJob.Constants.JOB_START_TIME;


@Component
public class EmployeeFeedJobCompletionNotificationListener extends JobExecutionListenerSupport {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeFeedJobCompletionNotificationListener.class);

    @Override
    public void afterJob(JobExecution jobExecution) {
        if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
            long startTime = Long.parseLong(
                    Objects.requireNonNull(jobExecution.getJobParameters().getString(JOB_START_TIME)));
            LOGGER.info("Time took to finish the job is [{}] ms", System.currentTimeMillis() - startTime);
        }
    }
}
