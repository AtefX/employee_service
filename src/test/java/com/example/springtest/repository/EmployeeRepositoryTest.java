package com.example.springtest.repository;

import com.example.springtest.model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;


@DataJpaTest
class EmployeeRepositoryTest {
    @Autowired
    private EmployeeRepository employeeRepository;
    private Employee employee;

    @BeforeEach
    public void initEmployee() {
        employee = Employee
                .builder()
                .firstName("Atef")
                .lastName("Azouzi")
                .email("atef.azouzi@enis.tn")
                .build();
    }

    //junit for test save employee
    @DisplayName("unit for test save employee")
    @Test
    public void givenEmployeeObject_whenSave_thenReturnSavedEmployee() {

        //given - precondition or setup

        //when  - action or the behavior that we are going to test
        Employee expectedEmployee = employeeRepository.save(employee);
        //then  - verify the uotput
        assertThat(expectedEmployee).isNotNull();
        assertThat(expectedEmployee.getId()).isGreaterThan(0);
    }

    @DisplayName("unit test for getAll Emplyee")
    @Test
    public void givenEmployeeLists_whenFindAll_thenReturnEmployeesLists() {


        Employee actualEmployee2 = Employee
                .builder()
                .firstName("Fourat")
                .lastName("ben hassine")
                .email("fourat.azouzi@enis.tn")
                .build();
        employeeRepository.save(employee);
        employeeRepository.save(actualEmployee2);
        //when  - action or the behavior that we are going to test
        List<Employee> expectedListOfEmployee = employeeRepository.findAll();
        //then  - verify the otput
        assertThat(expectedListOfEmployee).isNotNull();
        assertThat(expectedListOfEmployee).hasSize(2);
    }

    @DisplayName("test get employee by id")
    @Test
    public void givenEmployeeObject_whenFindBy_thenReturnEmploueeObject() {

        employeeRepository.save(employee);
        //when  - action or the behavior that we are going to test
        Optional<Employee> employeeDb = employeeRepository.findById(employee.getId());
        //then  - verify the uotput
        assertThat(employeeDb.isPresent()).isTrue();
        assertThat(employeeDb.get()).isNotNull();

    }

    @DisplayName("unit for findByEmail method")
    @Test
    public void givenEmployeeEmail_whenFindByEmail_thenReturnEmployeeObject() {

        //given - precondition or setup

        employeeRepository.save(employee);

        //when  - action or the behavior that we are going to test
        Optional<Employee> employeeDb = employeeRepository.findByEmail(employee.getEmail());
        //then  - verify the uotput
        assertThat(employeeDb.get()).isNotNull();
    }

    @DisplayName("unit test  for update Employee")
    @Test
    public void givenEmployeeObject_whenUpdateObject_thenReturnObjectUpdated() {

        //given - precondition or setup

        employeeRepository.save(employee);
        //when  - action or the behavior that we are going to test
        Employee savedEmployee = employeeRepository.findById(employee.getId()).get();
        savedEmployee.setEmail("atef.azouzi1994@gmail.com");
        savedEmployee.setFirstName("az");
        Employee updatedEmployee = employeeRepository.save(savedEmployee);

        //then  - verify the uotput

        assertAll("assert that all field are updated",
                () -> assertThat(updatedEmployee.getEmail()).isEqualTo(savedEmployee.getEmail()),
                () -> assertThat(updatedEmployee.getFirstName()).isEqualTo(savedEmployee.getFirstName()
                ));

    }


    @DisplayName("unit test for delete employee..")
    @Test
    public void givenEmployeeObject_whenDeleteEmployee_thenReturnNullOBject() {

        //given - precondition or setup
        employeeRepository.save(employee);
        //when  - action or the behavior that we are going to test
        employeeRepository.delete(employee);
        Optional<Employee> expected = employeeRepository.findById(employee.getId());
        //then  - verify the uotput
        assertThat(expected).isEmpty();

    }

    @DisplayName("unit test for delete employee by id..")
    @Test
    public void givenEmployeeObject_whenDeleteEmployeeById_thenReturnNullOBject() {

        //given - precondition or setup

        employeeRepository.save(employee);
        //when  - action or the behavior that we are going to test
        employeeRepository.deleteById(employee.getId());
        Optional<Employee> expected = employeeRepository.findById(employee.getId());
        //then  - verify the uotput
        assertThat(expected).isEmpty();

    }

    @DisplayName("unit for findByJQPL")
    @Test
    public void givenEmployeeObject_whenFindByJQPL_thenReturnEmployeeWithFirstnameAndLastname() {

        //given - precondition or setup

        employeeRepository.save(employee);
        //when  - action or the behavior that we are going to test
        Employee employee1 = employeeRepository.findByJPQL("Atef", "Azouzi");
        //then  - verify the uotput
        assertThat(employee1).isNotNull();
        assertAll("assert that employee have same first and last name",
                () -> assertThat(employee1.getFirstName()).isEqualTo(employee.getFirstName()),
                () -> assertThat(employee1.getLastName()).isEqualTo(employee.getLastName())
        );
    }

    @DisplayName("unit for findByJQPL native")
    @Test
    public void givenEmployeeObject_whenFindByJQPLNative_thenReturnEmployeeWithFirstnameAndLastname() {

        //given - precondition or setup

        employeeRepository.save(employee);
        //when  - action or the behavior that we are going to test
        Employee employee1 = employeeRepository.findByJPQLNative("Atef", "Azouzi");
        //then  - verify the uotput
        assertThat(employee1).isNotNull();
        assertAll("assert that employee have same first and last name",
                () -> assertThat(employee1.getFirstName()).isEqualTo(employee.getFirstName()),
                () -> assertThat(employee1.getLastName()).isEqualTo(employee.getLastName())
        );
    }

    @DisplayName("unit for findByJQPL native named")
    @Test
    public void givenEmployeeObject_whenFindByJQPLNativeNamed_thenReturnEmployeeWithFirstnameAndLastname() {

        //given - precondition or setup

        employeeRepository.save(employee);
        //when  - action or the behavior that we are going to test
        Employee employee1 = employeeRepository.findByJPQLNativeNamed("Atef", "Azouzi");
        //then  - verify the uotput
        assertThat(employee1).isNotNull();
        assertAll("assert that employee have same first and last name",
                () -> assertThat(employee1.getFirstName()).isEqualTo(employee.getFirstName()),
                () -> assertThat(employee1.getLastName()).isEqualTo(employee.getLastName())
        );
    }
}