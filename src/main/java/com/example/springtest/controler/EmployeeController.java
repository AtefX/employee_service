package com.example.springtest.controler;

import com.example.springtest.model.Employee;
import com.example.springtest.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    private EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Employee createEmployee(@RequestBody Employee employee) {
        return employeeService.save(employee);
    }

    @GetMapping("/getAllEmployees")
    public List<Employee> getAllEmployee() {
        return employeeService.getAllEmployee();
    }

    @GetMapping({"/getEmployee/{id}"})
    public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") long id) {
        return employeeService.getById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable("id") long id, @RequestBody Employee employee) {
        return employeeService.getById(id).map(savedEmployee -> {
            savedEmployee.setFirstName(employee.getFirstName());
            savedEmployee.setEmail(employee.getEmail());
            savedEmployee.setLastName(employee.getLastName());
            employeeService.save(savedEmployee);
            return ResponseEntity.ok().body(savedEmployee);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable("id") long id) {
        employeeService.deleteEmployeeById(id);
        return new ResponseEntity<String>("Employee Deleted Succesufuly", HttpStatus.OK);
    }

}
