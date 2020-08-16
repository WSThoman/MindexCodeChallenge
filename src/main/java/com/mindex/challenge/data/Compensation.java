package com.mindex.challenge.data;

import java.util.*;

// For Task #2, I interpreted line 81 of the 'README.md' file
// to mean 'employeeId' instead of 'employee' as one of the
// Compensation fields.
// In a normalized database, we would not want to duplicate
// 'employee' data within a Compensation record, preferring instead
// to store and query Compensation records by 'employeeId'.
//
// Also, the 'create' endpoint in this sample is not idempotent
// as we would want in a full application, so multiple 'create'
// calls cause 'non unique result' errors to be returned
// in the 'read' calls.
//
public class Compensation {
	private String employeeId;
    private double salary;
    private Date effectiveDate;
    
    public Compensation() {
    }

    public String getEmployeeId() {
	  return employeeId;
	}
	
	public void setEmployeeId(String employeeId) {
	  this.employeeId = employeeId;
	}

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

}
