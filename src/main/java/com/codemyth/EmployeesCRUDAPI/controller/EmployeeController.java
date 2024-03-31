package com.codemyth.EmployeesCRUDAPI.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.codemyth.EmployeesCRUDAPI.model.Employee;
import com.codemyth.EmployeesCRUDAPI.model.ResponseObject;
import com.codemyth.EmployeesCRUDAPI.model.ResponseObjectWithEmployee;
import com.codemyth.EmployeesCRUDAPI.model.ResponseObjectWithoutEmployees;

import org.springframework.web.bind.annotation.PathVariable;
import com.codemyth.EmployeesCRUDAPI.repository.EmployeeRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.codemyth.EmployeesCRUDAPI.model.Employee;

import java.util.*;

@RestController
@RequestMapping("/api")
@Tag(name="EmployeeController", description="To perform crud operations on employees table")
public class EmployeeController {

	@Autowired
	EmployeeRepository employeeRepository;

	@Operation(
		summary = "POST operation on employees",
		description = "It is used to save employee object in employees table"
	)
	@PostMapping("/employees")
	public ResponseEntity<ResponseObjectWithoutEmployees> CreateNewEmployee(@RequestBody Employee employee) {
		employeeRepository.save(employee);
		ResponseObjectWithoutEmployees responseObj = new ResponseObjectWithoutEmployees();
		responseObj.setStatus(true);
		responseObj.setMessage("New Employee Created.");
		ResponseEntity<ResponseObjectWithoutEmployees> response = new ResponseEntity<ResponseObjectWithoutEmployees>(responseObj,HttpStatus.OK);
		return response;
	}
	
	@Operation(
			summary = "GET operation on employees",
			description = "It is used to fetch all employees from employees table"
		)
	@GetMapping("/employees")
	public ResponseEntity<ResponseObject> getAllEmployees(){
		ResponseObject responseObj = new ResponseObject();
		List<Employee> empList = new ArrayList<>();
		employeeRepository.findAll().forEach(empList::add);
		responseObj.setStatus(true);
		responseObj.setMessage("All Employees Fetched Successfully.");
		responseObj.setEmployees(empList);
		ResponseEntity<ResponseObject> response = new ResponseEntity<ResponseObject>(responseObj,HttpStatus.OK);
		return response;
	}
	
	@Operation(
			summary = "GET operation on employees using Employee Id",
			description = "It is used to fetch an employee details by the empid from employees table"
		)
	@GetMapping("/employees/{empid}")
	public ResponseEntity<ResponseObjectWithEmployee> getEmployeeById(@PathVariable("empid") Integer empid){
		ResponseObjectWithEmployee responseObj = new ResponseObjectWithEmployee();
		Optional<Employee> emp = employeeRepository.findById(empid);
		ResponseEntity<ResponseObjectWithEmployee> response = null;
		if(emp.isPresent()) {
			responseObj.setStatus(true);
			responseObj.setMessage("Employee with id: "+ empid +" is fetched successfully.");
			responseObj.setEmployee(emp.get());
			response = new ResponseEntity<ResponseObjectWithEmployee>(responseObj,HttpStatus.OK);	
		}
		else {
			responseObj.setStatus(false);
			responseObj.setMessage("Employee with id: "+ empid +" not found.");
			responseObj.setEmployee(null);
			response = new ResponseEntity<ResponseObjectWithEmployee>(responseObj,HttpStatus.NOT_FOUND);
		}
		return response;
	}
	
	@Operation(
			summary = "PUT operation on employees using Employee Id",
			description = "It is used to update an employee details by the empid in employees table"
		)
	@PutMapping("/employees/{empid}")
	public ResponseEntity<ResponseObjectWithoutEmployees> updateEmployeeById(@PathVariable("empid") Integer empid, @RequestBody Employee employee){
		ResponseObjectWithoutEmployees responseObj = new ResponseObjectWithoutEmployees();
		Optional<Employee> emp = employeeRepository.findById(empid);
		ResponseEntity<ResponseObjectWithoutEmployees> response = null;
		if(emp.isPresent()) {
			Employee emp1 = (Employee)emp.get();
			emp1.setEmp_age(employee.getEmp_age());
			emp1.setEmp_name(employee.getEmp_name());
			emp1.setEmp_city(employee.getEmp_city());
			emp1.setEmp_salary(employee.getEmp_salary());
			employeeRepository.save(emp1);
			responseObj.setStatus(true);
			responseObj.setMessage("Employee with id: "+ empid +" is updated successfully.");
			response = new ResponseEntity<ResponseObjectWithoutEmployees>(responseObj,HttpStatus.OK);	
		}
		else {
			responseObj.setStatus(false);
			responseObj.setMessage("Employee with id: "+ empid +" not found.");
			response = new ResponseEntity<ResponseObjectWithoutEmployees>(responseObj,HttpStatus.NOT_FOUND);
		}
		return response;
	}
	
	@Operation(
			summary = "DELETE operation on employees using Employee Id",
			description = "It is used to delete an employee details by the empid from employees table"
		)
	@DeleteMapping("/employees/{empid}")
	public ResponseEntity<ResponseObjectWithoutEmployees> deleteEmployeeById(@PathVariable("empid") Integer empid){
		ResponseObjectWithoutEmployees responseObj = new ResponseObjectWithoutEmployees();
		Optional<Employee> emp = employeeRepository.findById(empid);
		ResponseEntity<ResponseObjectWithoutEmployees> response = null;
		if(emp.isPresent()) {
			Employee emp1 = (Employee)emp.get();
			employeeRepository.deleteById(emp1.getEmpid());
			responseObj.setStatus(true);
			responseObj.setMessage("Employee with id: "+ empid +" is deleted successfully.");
			response = new ResponseEntity<ResponseObjectWithoutEmployees>(responseObj,HttpStatus.OK);	
		}
		else {
			responseObj.setStatus(false);
			responseObj.setMessage("Employee with id: "+ empid +" not found.");
			response = new ResponseEntity<ResponseObjectWithoutEmployees>(responseObj,HttpStatus.NOT_FOUND);
		}
		return response;
	}
	
	@Operation(
			summary = "DELETE operation on employees",
			description = "It is used to delete all employees from employees table"
		)
	@DeleteMapping("/employees")
	public ResponseEntity<ResponseObjectWithoutEmployees> deleteAllEmployees(){
		ResponseObjectWithoutEmployees responseObj = new ResponseObjectWithoutEmployees();
		ResponseEntity<ResponseObjectWithoutEmployees> response = null;
			employeeRepository.deleteAll();
			responseObj.setStatus(true);
			responseObj.setMessage("All employees are deleted successfully.");
			response = new ResponseEntity<ResponseObjectWithoutEmployees>(responseObj,HttpStatus.OK);	
		return response;
	}
	
	@Operation(
			summary = "GET operation on employees using Employee City",
			description = "It is used to fetch employees by their city from employees table"
		)
	@GetMapping("/employee")
	public ResponseEntity<ResponseObject> getAllEmployeesByCity(@RequestParam("city") String city){
		ResponseObject responseObj = new ResponseObject();
		List<Employee> empList = new ArrayList<>();
		List<Employee> empSearchCityList = new ArrayList<>();
		ResponseEntity<ResponseObject> response = null;
		employeeRepository.findAll().forEach(empList::add);
		if(city==null) {
			responseObj.setStatus(false);
			responseObj.setMessage("City can't be null.");
			responseObj.setEmployees(null);
			response = new ResponseEntity<ResponseObject>(responseObj,HttpStatus.BAD_REQUEST);		
		}
		for(Employee e : empList) {
			if(e.getEmp_city().equalsIgnoreCase(city)) {
				empSearchCityList.add(e);
			}
			else {
				continue;
			}
		}
		if(!empSearchCityList.isEmpty()) {
			responseObj.setStatus(true);
			responseObj.setMessage("All Employees of city: "+city+" are fetched Successfully.");
			responseObj.setEmployees(empSearchCityList);
			response = new ResponseEntity<ResponseObject>(responseObj,HttpStatus.OK);		
		}
		else {
			responseObj.setStatus(false);
			responseObj.setMessage("No Employees of city: "+city+" are found.");
			responseObj.setEmployees(null);
			response = new ResponseEntity<ResponseObject>(responseObj,HttpStatus.NOT_FOUND);		
		}
		return response;
	}
	
	@Operation(
			summary = "GET operation on employees using Employee Age",
			description = "It is used to fetch employees by their age from employees table"
		)
	@GetMapping("/employeeAge")
	public ResponseEntity<ResponseObject> getAllEmployeesByAge(@RequestParam("age") String age){
		ResponseObject responseObj = new ResponseObject();
		List<Employee> empList = new ArrayList<>();
		List<Employee> empSearchAgeList = new ArrayList<>();
		ResponseEntity<ResponseObject> response = null;
		employeeRepository.findAll().forEach(empList::add);
		if(age==null) {
			responseObj.setStatus(false);
			responseObj.setMessage("Age can't be null.");
			responseObj.setEmployees(null);
			response = new ResponseEntity<ResponseObject>(responseObj,HttpStatus.BAD_REQUEST);		
		}
		for(Employee e : empList) {
			if(e.getEmp_age()==Integer.parseInt(age)) {
				empSearchAgeList.add(e);
			}
			else {
				continue;
			}
		}
		if(!empSearchAgeList.isEmpty()) {
			responseObj.setStatus(true);
			responseObj.setMessage("All Employees of age: "+age+" are fetched Successfully.");
			responseObj.setEmployees(empSearchAgeList);
			response = new ResponseEntity<ResponseObject>(responseObj,HttpStatus.OK);		
		}
		else {
			responseObj.setStatus(false);
			responseObj.setMessage("No Employees of city: "+age+" are found.");
			responseObj.setEmployees(null);
			response = new ResponseEntity<ResponseObject>(responseObj,HttpStatus.NOT_FOUND);		
		}
		return response;
	}
	
}
