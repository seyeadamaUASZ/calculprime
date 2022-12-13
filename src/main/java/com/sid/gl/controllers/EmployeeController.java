package com.sid.gl.controllers;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api")
public class EmployeeController {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job job;


}
