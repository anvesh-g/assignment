package com.corptec.assignment.batchJob;

import com.corptec.assignment.utility.FileSystemUtility;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import javax.sql.DataSource;

import java.io.File;
import java.util.Arrays;
import java.util.Objects;

import static com.corptec.assignment.batchJob.Constants.EMPLOYEE_HEADERS;
import static com.corptec.assignment.batchJob.Constants.INSERT_QUERY;
import static com.corptec.assignment.batchJob.Constants.READ_AND_WRITE_FILE;
import static com.corptec.assignment.batchJob.Constants.JOB_NAME;


@Configuration
@EnableBatchProcessing
public class EmployeeFeedBatchJob {

    private final JobBuilderFactory jobBuilderFactory;

    private final StepBuilderFactory stepBuilderFactory;

    private final EmployeeRecordFeedSkipPolicy employeeRecordFeedSkipPolicy;

    private final EmployeeFeedJobCompletionNotificationListener employeeFeedJobCompletionNotificationListener;

    public DataSource dataSource;

    private final FileSystemUtility fileSystemUtility;

    @Autowired
    public EmployeeFeedBatchJob(JobBuilderFactory jobBuilderFactory,
                                StepBuilderFactory stepBuilderFactory,
                                EmployeeRecordFeedSkipPolicy employeeRecordFeedSkipPolicy,
                                EmployeeFeedJobCompletionNotificationListener employeeFeedJobCompletionNotificationListener,
                                DataSource dataSource,
                                FileSystemUtility fileSystemUtility) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.employeeRecordFeedSkipPolicy = employeeRecordFeedSkipPolicy;
        this.employeeFeedJobCompletionNotificationListener = employeeFeedJobCompletionNotificationListener;
        this.dataSource = dataSource;
        this.fileSystemUtility = fileSystemUtility;
    }

    @Bean
    public FlatFileItemReader<EmployeeLineMapper> employeeItemReader() {
        FlatFileItemReader<EmployeeLineMapper> reader = new FlatFileItemReader<>();
        reader.setLinesToSkip(1);
        DefaultLineMapper<EmployeeLineMapper> customerLineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setNames(EMPLOYEE_HEADERS);
        customerLineMapper.setLineTokenizer(tokenizer);
        customerLineMapper.setFieldSetMapper(new EmployeeFieldSetMapper());
        customerLineMapper.afterPropertiesSet();
        reader.setLineMapper(customerLineMapper);
        return reader;
    }

    public EmployeeRecordFeedProcessor employeeRecordFeedProcessor() {
        return new EmployeeRecordFeedProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<EmployeeLineMapper> employeeItemWriter() {
        JdbcBatchItemWriter<EmployeeLineMapper> itemWriter = new JdbcBatchItemWriter<>();
        itemWriter.setDataSource(this.dataSource);
        itemWriter.setSql(INSERT_QUERY);
        itemWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        itemWriter.afterPropertiesSet();
        return itemWriter;
    }


    @Bean(value = READ_AND_WRITE_FILE)
    public Step readAndWriteFileStep() {
        return stepBuilderFactory.get(READ_AND_WRITE_FILE)
                .<EmployeeLineMapper, EmployeeLineMapper>chunk(10)
                .reader(multiResourceItemReader())
                .processor(employeeRecordFeedProcessor())
                .writer(employeeItemWriter())
                .faultTolerant()
                .skipPolicy(employeeRecordFeedSkipPolicy)
                .build();
    }

    @Bean
    public MultiResourceItemReader<EmployeeLineMapper> multiResourceItemReader() {
        MultiResourceItemReader<EmployeeLineMapper> resourceItemReader = new MultiResourceItemReader<>();
        resourceItemReader.setResources(getResources());
        resourceItemReader.setDelegate(employeeItemReader());
        return resourceItemReader;
    }

    @Bean(value = JOB_NAME)
    public Job employeeRecordFeed() {
        return jobBuilderFactory.get(JOB_NAME)
                .listener(employeeFeedJobCompletionNotificationListener)
                .start(readAndWriteFileStep())
                .build();
    }

    public Resource[] getResources() {
        File directory = fileSystemUtility.getFilesLocation();
        int countOfFiles = (int) Arrays.stream(Objects.requireNonNull(directory).listFiles())
                .filter(x -> x.isFile() && x.getName().endsWith(".csv"))
                .count();
        Resource[] resources = new Resource[countOfFiles];
        int i = 0;
        for (File file : Objects.requireNonNull(directory.listFiles())) {
            if(file.isFile() && file.getName().endsWith(".csv")) {
                resources[i] = new FileSystemResource(file);
                i++;
            }
        }
        return resources;
    }
}
