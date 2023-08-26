package com.sid.gl.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "api")
public class EmployeeController {
    private Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job job;

    @GetMapping("/calculprime")
    public BatchStatus calcul() throws Exception{
        Map<String, JobParameter> jobParameterMap=
                new HashMap<>();
        jobParameterMap.put("time",new JobParameter(System.currentTimeMillis()));
        JobParameters parameters=
                new JobParameters(jobParameterMap);
        JobExecution jobExecution =
                jobLauncher.run(job,parameters);
        while(jobExecution.isRunning()){
          logger.info("....execution job ....");

        }
        return jobExecution.getStatus();
    }


}
