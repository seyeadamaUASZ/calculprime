package com.sid.gl.process;

import com.sid.gl.constants.PrimeConstant;
import com.sid.gl.exceptions.MontantException;
import com.sid.gl.models.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class EmployeeProcessor implements ItemProcessor<Employee,Employee> {
    private Logger logger = LoggerFactory.getLogger(EmployeeProcessor.class);

    @Override
    public Employee process(Employee employee) throws Exception {
        double montantTotal = PrimeConstant.GLOBAL_PRIME;
        //verifier la notation accordée
        double prime=0.0;
        if(employee.getNotation_ceo().equals("PM")){
            prime += employee.getSalary() + (2* employee.getSalary());
        }
        if(employee.getNotation_ceo().equals("HE")){
            prime += employee.getSalary() + (1.5* employee.getSalary());
        }
        if(employee.getNotation_ceo().equals("PP")){
            prime += 2 * employee.getSalary();
        }
        if(employee.getNotation_ceo().equals("DA")){
            prime += employee.getSalary() + (0.5* employee.getSalary());
        }

        employee.setPrime(prime);

        montantTotal = montantTotal - prime;

        if(montantTotal<=0){
            throw new MontantException("amount is full empty !!!");
        }
        logger.info("amount global {}",montantTotal);

        return employee;
    }
}
