package com.corptec.assignment.batchJob;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.batch.core.step.skip.SkipLimitExceededException;
import org.springframework.batch.core.step.skip.SkipPolicy;
import org.springframework.stereotype.Component;

@Component
public class EmployeeRecordFeedSkipPolicy implements SkipPolicy {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeRecordFeedSkipPolicy.class);

    @Override
    public boolean shouldSkip(Throwable throwable, int skipCount)
            throws SkipLimitExceededException {
        if(throwable instanceof Exception) {
            LOGGER.error("Skipping record due to exception [{}], records skipped as of now [{}]",
                    throwable, skipCount+1);
            return true;
        }
        return false;
    }
}
