package com.example.springtest.controler;

import com.example.springtest.model.Employee;
import com.example.springtest.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private EmployeeService employeeService;
    @Autowired
    private ObjectMapper objectMapper;
    private Employee employee;

    @BeforeEach
    public void setup() {
        employee = Employee.builder().id(1l).firstName("Atef").lastName("Azouzi").email("atef.azouzi@enis.tn").build();
    }

    @DisplayName("unit test  for save employee rest controller ..")
    @Test
    public void givenEmployeeObject_whenSaveEmployee_thenReturnEmployeeObject() throws Exception {

        //given - precondition or setup

        given(employeeService.save(ArgumentMatchers.any(Employee.class))).willAnswer((invocation) -> invocation.getArgument(0));
        //when  - action or the behavior that we are going to test
        ResultActions perform = mockMvc.perform(post("/api/employees/create").contentType(MediaType.APPLICATION_JSON)
                                                                             .content(objectMapper.writeValueAsString(employee)));
        // then
        perform.andExpect(status().isCreated())
               .andExpect(jsonPath("$.firstName", CoreMatchers.is(employee.getFirstName())))
               .andExpect(jsonPath("$.lastName", CoreMatchers.is(employee.getLastName())))
               .andExpect(jsonPath("$.email", CoreMatchers.is(employee.getEmail())));
    }

    @DisplayName("unit test  for getAll Employees Controler ..")
    @Test
    public void givenListOfEmployees_whenGetAllEmployye_thenReturnEmployeesLists() throws Exception {

        //given - precondition or setup
        List<Employee> employees = new ArrayList<>();
        employees.add(employee);
        employees.add(Employee.builder().firstName("fourat").lastName("ben hassine").email("fourat@gmail.com").build());
        given(employeeService.getAllEmployee()).willReturn(employees);
        //when  - action or the behavior that we are going to test
        ResultActions perform = mockMvc.perform(get("/api/employees/getAllEmployees"));

        //then  - verify the uot qscddsput
        perform.andExpect(MockMvcResultMatchers.status().isOk()).andDo(print())
               .andExpect(jsonPath("$.size()", CoreMatchers.is(employees.size())));

    }

    @DisplayName("unit positive test  for getById ..")
    @Test
    public void givenValidEmployeeId_whengetById_thenRerturnEmployee() throws Exception {

        //given - precondition or setup
        long employyeId = 1l;
        given(employeeService.getById(employyeId)).willReturn(Optional.of(employee));
        //when  - action or the behavior that we are going to test
        ResultActions perform = mockMvc.perform(get("/api/employees/getEmployee/{id}", employyeId));
        //then  - verify the uotput
        perform.andExpect(status().isOk()).andDo(print())
               .andExpect(jsonPath("$.firstName", CoreMatchers.is(employee.getFirstName())))
               .andExpect(jsonPath("$.lastName", CoreMatchers.is(employee.getLastName())))
               .andExpect(jsonPath("$.email", CoreMatchers.is(employee.getEmail())));
    }

    @DisplayName("unit negative test  for getById ..")
    @Test
    public void givenInvalidEmployeeId_whengetById_thenRerturnNotFound() throws Exception {
        long employyeId = 1l;
        given(employeeService.getById(employyeId)).willReturn(Optional.empty());
        //when  - action or the behavior that we are going to test
        ResultActions perform = mockMvc.perform(get("/api/employees/getEmployee/{id}", employyeId));
        //then  - verify the uotput
        perform.andExpect(status().isNotFound()).andDo(print());
    }

    @DisplayName("unit test for update employee controler ..")
    @Test
    public void givenIdAndEmployeeObject_whenSaveEmployee_thenReturnSavedEmployeeAndResponseOk() throws Exception {

        //given - precondition or setup
        long employeeId = 1l;
        Employee employeeToUpdate = Employee.builder().firstName("AtefUo").lastName("AZouziUp").build();
        given(employeeService.getById(employeeId)).willReturn(Optional.of(employee));
        given(employeeService.updateEmployee(ArgumentMatchers.any(Employee.class))).willAnswer((invocation) -> invocation.getArgument(0));

        //when  - action or the behavior that we are going to test
        ResultActions response = mockMvc.perform(put("/api/employees//update/{id}", employeeId).contentType(MediaType.APPLICATION_JSON)
                                                                                               .content(objectMapper.writeValueAsString(employeeToUpdate)));
        //then  - verify the uotput
        response.andExpect(status().isOk()).andDo(print())
                .andExpect(jsonPath("$.firstName", CoreMatchers.is(employeeToUpdate.getFirstName())))
                .andExpect(jsonPath("$.lastName", CoreMatchers.is(employeeToUpdate.getLastName())));
    }

    @DisplayName("unit test negative for update employee controler ..")
    @Test
    public void givenIdAndEmployeeObject_whenSaveEmployee_thenReturnSavedEmployeeAndResponsenotFound() throws Exception {

        //given - precondition or setup
        long employeeId = 1l;
        Employee employeeToUpdate = Employee.builder().firstName("AtefUo").lastName("AZouziUp").build();
        given(employeeService.getById(employeeId)).willReturn(Optional.empty());

        //when  - action or the behavior that we are going to test
        ResultActions response = mockMvc.perform(put("/api/employees/update/{id}", employeeId).contentType(MediaType.APPLICATION_JSON)
                                                                                              .content(objectMapper.writeValueAsString(employeeToUpdate)));
        //then  - verify the uotput
        verify(employeeService, never()).updateEmployee(any());
        response.andExpect(status().isNotFound()).andDo(print());

    }

    @DisplayName("unit test  for delete employee Controler positive scenario..")
    @Test
    public void givenEmployeeId_whenDeleteEmployee_thenReturnOkResponse() throws Exception {

        //given - precondition or setup
        long employeeId = 1l;
        willDoNothing().given(employeeService).deleteEmployeeById(employeeId);
        //when  - action or the behavior that we are going to test
        ResultActions perform = mockMvc.perform(delete("/api/employees/delete/{id}", employeeId));
        //then  - verify the uotput
        perform.andExpect(status().isOk()).andDo(print()).andExpect(content().string("Employee Deleted Succesufuly"));
    }

    @DisplayName("unit test  for delete employee Controler negative scenario..")
    @Test
    public void givenEmployeeId_whenDeleteEmployee_thenReturnbadResponse() throws Exception {

        //given - precondition or setup
        long employeeId = 1l;
        willDoNothing().given(employeeService).deleteEmployeeById(employeeId);
        //when  - action or the behavior that we are going to test
        ResultActions perform = mockMvc.perform(delete("/api/employees/delete/{id}", employeeId));
        //then  - verify the uotput
        perform.andExpect(status().isOk()).andDo(print()).andExpect(content().string("Employee Deleted Succesufuly"));
    }
}