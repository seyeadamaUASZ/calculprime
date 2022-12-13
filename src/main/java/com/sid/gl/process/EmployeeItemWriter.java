package com.sid.gl.process;

import com.sid.gl.models.Employee;
import com.sid.gl.repositories.EmployeeRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EmployeeItemWriter implements ItemWriter<Employee> {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Override
    public void write(List<? extends Employee> list) throws Exception {
         employeeRepository.saveAll(list);
    }
}
