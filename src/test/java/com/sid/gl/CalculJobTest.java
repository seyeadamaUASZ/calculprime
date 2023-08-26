package com.sid.gl;

import com.sid.gl.process.BatchConfig;
import com.sid.gl.process.EmployeeItemWriter;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.runner.RunWith;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBatchTest
@DirtiesContext
@EnableAutoConfiguration
@SpringJUnitConfig(BatchConfig.class)
@PropertySource("classpath:application.properties")
public class CalculJobTest {
    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private JobRepositoryTestUtils jobRepositoryTestUtils;

    @MockBean
    private EmployeeItemWriter employeeItemWriter; //use mockBean for a component class interact for test

    @AfterEach
    public void cleanUp(){
      jobRepositoryTestUtils.removeJobExecutions();
    }

    @Test
    public void should_execute_calcul_prime_completed() throws Exception {
        JobExecution jobExecution = jobLauncherTestUtils.launchJob();
        JobInstance jobInstance = jobExecution.getJobInstance();
        ExitStatus exitStatus = jobExecution.getExitStatus();
        assertEquals("employee-data-load-job",jobInstance.getJobName());
        assertEquals("COMPLETED",exitStatus.getExitCode());
    }



}
