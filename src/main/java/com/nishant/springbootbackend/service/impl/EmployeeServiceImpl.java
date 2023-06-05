package com.nishant.springbootbackend.service.impl;

import com.nishant.springbootbackend.exception.ResourceNotFoundException;
import com.nishant.springbootbackend.model.Employee;
import com.nishant.springbootbackend.repository.EmployeeRepository;
import com.nishant.springbootbackend.service.EmployeeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        super();
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee getEmployeeById(long id) {
        Optional<Employee> employee = employeeRepository.findById(id);
       if(employee.isPresent()){
           return employee.get();
       }else {
           throw new ResourceNotFoundException("Employee", "Id",id);
       }


    }

    @Override
    public Employee updateEmployee(Employee employee, long id) {

        //we need to check if employee with Id exists in a database

        Employee existingEmployee = employeeRepository.
                findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee","Id",id));

        existingEmployee.setFirstName(employee.getFirstName());
        existingEmployee.setLastName(employee.getLastName());
        existingEmployee.setEmail(employee.getEmail());

        // save employee to database
        employeeRepository.save(existingEmployee);
        return existingEmployee;
    }

    @Override
    public void deleteEmployee(long id) {

        // check employee exist in a database
        employeeRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Employee","Id",id));


        employeeRepository.deleteById(id);
    }
}
