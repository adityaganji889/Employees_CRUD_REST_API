package com.codemyth.EmployeesCRUDAPI.repository;



import org.springframework.data.jpa.repository.JpaRepository;


import com.codemyth.EmployeesCRUDAPI.model.Employee;


public interface EmployeeRepository extends JpaRepository<Employee, Integer>  {
	
}
