package com.example.springtest.service;

import com.example.springtest.exeption.EmployeeNotFoundException;
import com.example.springtest.model.Employee;
import com.example.springtest.repository.EmployeeRepository;
import com.example.springtest.service.impl.EmployeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {
    @Mock
    private EmployeeRepository employeeRepository;
    @InjectMocks
    private EmployeeServiceImpl employeeServiceImpl;
    private Employee actual;

    @BeforeEach
    public void setUp() {
        actual = Employee.builder().id(1l).firstName("Atef").lastName("Azouzi").email("atef.azouzi@enis.tn").build();
    }

    @DisplayName("unit for save employee method")
    @Test
    public void givenEmployeeObject_whenSaveEmployee_thenReturnEmployeeObject() {

        //given - precondition or setup
        given(employeeRepository.save(actual)).willReturn(actual);
        given(employeeRepository.findByEmail(actual.getEmail())).willReturn(Optional.empty());
        //when  - action or the behavior that we are going to test
        Employee expected = employeeServiceImpl.save(actual);
        //then  - verify the uotput
        assertThat(expected).isEqualTo(actual);
    }

    @DisplayName("unit testt  for save employee when existing email ..")
    @Test
    public void givenExistEmail_whenSaveEmployee_thenThrowsEmployeeNotFoundException() {
        //given - precondition or setup
        given(employeeRepository.findByEmail(actual.getEmail())).willReturn(Optional.of(actual));
        //when  - action or the behavior that we are going to test
        //then  - verify the uotput
        assertThatThrownBy(() -> employeeServiceImpl.save(actual)).isInstanceOf(EmployeeNotFoundException.class)
                                                                  .hasMessageContaining("employee exist with given email" + actual.getEmail());

        verify(employeeRepository, never()).save(any());
    }

    @DisplayName("unit test for getAll employye method ..")
    @Test
    public void givenEmployeeLists_whenGetAll_thenReturnListOfEmployee() {

        //given - precondition or setup
        Employee employee2 = Employee.builder().id(2l).firstName("hamid").lastName("ali").email("hamid.azouzi@enis.tn")
                                     .build();
        given(employeeRepository.findAll()).willReturn(List.of(actual, employee2));
        //when  - actiofn or the behavior that we are going to test
        List<Employee> expected = employeeServiceImpl.getAllEmployee();
        //then  - verify the uotput
        assertThat(expected).hasSize(2);
    }

    @DisplayName("unit test for getAll employye method .. negative scenario")
    @Test
    public void givenEmptyListOfEmployee_whenGetAll_thenReturnEmptyList() {

        //given - precondition or setup

        given(employeeRepository.findAll()).willReturn(Collections.emptyList());
        //when  - action or the behavior that we are going to test
        List<Employee> expected = employeeServiceImpl.getAllEmployee();
        //then  - verify the uotput
        assertThat(expected).isEmpty();
        assertThat(expected.size()).isEqualTo(0);
    }

    @DisplayName("unit test for getById metho ..")
    @Test
    public void givenEmplyeeId_whenGetEmployeeById_thenReturnEmployee() {

        //given - precondition or setup
        given(employeeRepository.findById(1l)).willReturn(Optional.of(actual));
        //when  - action or the behavior that we are going to test
        Optional<Employee> expected = employeeServiceImpl.getById(actual.getId());
        //then  - verify the uotput
        assertThat(expected).isNotNull();
    }

    @DisplayName("unit test for update employee service ..")
    @Test
    public void givenEmployeeObject_whenUpdateEmployee_thenRetrunUpdatedEmployee() {

        //given - precondition or setup
        given(employeeRepository.save(actual)).willReturn(actual);
        //when  - action or the behavior that we are going to test
        Employee expected = employeeServiceImpl.updateEmployee(actual);
        //then  - verify the uotput
        assertThat(expected).isEqualTo(actual);

    }

    @DisplayName("unit tes for delete by id method ..")
    @Test
    public void givenEmployeeId_whenDeleteById_thenNothing() {

        //given - precondition or setup
        long emplyeeId = 1l;
        willDoNothing().given(employeeRepository).deleteById(emplyeeId);
        //when  - action or the behavior that we are going to test
        employeeServiceImpl.deleteEmployeeById(emplyeeId);
        //then  - verify the uotput
        verify(employeeRepository, times(1)).deleteById(emplyeeId);

    }

    @DisplayName("unit test for find by email  ..")
    @Test
    public void givenEmployeeEmail_whenFindByEmail_thenReturnEmployee() {

        //given - precondition or setup
        given(employeeRepository.findByEmail(actual.getEmail())).willReturn(Optional.of(actual));
        //when  - action or the behavior that we are going to test
        Optional<Employee> expected = employeeServiceImpl.findByEmail(actual.getEmail());
        //then  - verify the uotput
        assertThat(expected).isPresent();

    }
}