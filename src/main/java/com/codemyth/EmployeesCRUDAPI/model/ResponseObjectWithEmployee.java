package com.codemyth.EmployeesCRUDAPI.model;

public class ResponseObjectWithEmployee {
	
	private boolean status;
	private String message;
	private Employee employee;
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	
}
