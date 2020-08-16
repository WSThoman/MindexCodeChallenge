package com.mindex.challenge.service.impl;

import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mindex.challenge.dao.EmployeeRepository;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.EmployeeService;
import com.mindex.challenge.service.ReportingStructureService;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

@Service
public class ReportingStructureServiceImpl implements ReportingStructureService {

    private static final Logger LOG = LoggerFactory.getLogger(ReportingStructureServiceImpl.class);

    private ReportingStructure reportingStructure = null;
	private int numDirectReports = 0;
    
    @Autowired
    private EmployeeRepository employeeRepository;
    
    @Override
    public ReportingStructure read(String employeeId) {
        LOG.debug("Calculating reporting structure for employeeId [{}]", employeeId);

        Employee employee = employeeRepository.findByEmployeeId(employeeId);

        if (employee == null) {
            throw new RuntimeException("Invalid employeeId: " + employeeId);
        }

        // Init the recursion members
        //
        reportingStructure = new ReportingStructure();
        reportingStructure.setEmployee(employee);
        
        numDirectReports = 0;
        
        // Recursively traverse the data for each employee's 'direct reports'
        //
        List<Employee> dirReports = GetDirectReportsInfo( employee.getDirectReports() );
       
        reportingStructure.getEmployee().setDirectReports( dirReports );
               
        // Save the number of 'direct reports' to our structure
        //
        LOG.debug("Final numDirectReports: {}", numDirectReports );

        reportingStructure.setNumberOfReports( numDirectReports );       
        
        // Return result to caller
        //
        return reportingStructure;
    }

    private List<Employee> GetDirectReportsInfo( List<Employee> dirReportsList )
    {
        for (Employee drEmployee : dirReportsList)
        {
        	Employee infoEmployee = employeeRepository.findByEmployeeId( drEmployee.getEmployeeId() );

            if (infoEmployee == null) {
                throw new RuntimeException( "Invalid employeeId: " + drEmployee.getEmployeeId() );
            }
            
        	drEmployee.setFirstName(  infoEmployee.getFirstName() );
        	drEmployee.setLastName(   infoEmployee.getLastName() );
        	drEmployee.setPosition(   infoEmployee.getPosition() );
        	drEmployee.setDepartment( infoEmployee.getDepartment() );
  
        	// Each employee in the list is a 'direct report', so increment the count
        	//
        	numDirectReports++;
        	
        	// Direct reports for this 'info' employee
        	//
            if (infoEmployee.getDirectReports() != null)
            {
            	List<Employee> subDirReports = GetDirectReportsInfo( infoEmployee.getDirectReports() );
            	
	        	drEmployee.setDirectReports( subDirReports );
            }
        }
        
        return dirReportsList;
    }    
}
