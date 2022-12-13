package com.sid.gl.process;

import com.sid.gl.models.Employee;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Autowired
    private JobBuilderFactory jobBuilder;

    @Autowired
    private StepBuilderFactory stepBuilder;

    @Autowired
    private ItemReader<Employee> employeeItemReader;

    @Autowired
    private ItemWriter<Employee> employeeItemWriter;

    //@Autowired
    //private ItemProcessor<Employee,Employee> employeeEmployeeItemProcessor;

    @Bean
    public Job buildJob(){
        Step step1 =
                stepBuilder.get("step-load-data")
                        .<Employee,Employee>chunk(100)
                        .reader(employeeItemReader)
                        .processor(processor())
                        .writer(employeeItemWriter)
                        .build();
        return jobBuilder.get("employee-data-load-job")
                .start(step1).build();
    }

    @Bean
    public EmployeeProcessor processor(){
        return new EmployeeProcessor();
    }



    @Bean
    public FlatFileItemReader<Employee> flatFileItemReader(){
         FlatFileItemReader<Employee> employeeFlatFileItemReader=
                 new FlatFileItemReader<>();
         employeeFlatFileItemReader.setName("EMP");
         employeeFlatFileItemReader.setLinesToSkip(1);
        employeeFlatFileItemReader.setResource(new FileSystemResource("src/main/resources/employees.csv"));
        employeeFlatFileItemReader.setLineMapper(lineMapper());
        return employeeFlatFileItemReader;
    }

    private LineMapper<Employee> lineMapper() {
        DefaultLineMapper<Employee> lineMapper =
           new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer =
             new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames("id","lastName","firstName","notation_ceo","salary");
        lineMapper.setLineTokenizer(lineTokenizer);
        BeanWrapperFieldSetMapper fieldSetMapper =
                new BeanWrapperFieldSetMapper();
        fieldSetMapper.setTargetType(Employee.class);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        return lineMapper;
    }

}
