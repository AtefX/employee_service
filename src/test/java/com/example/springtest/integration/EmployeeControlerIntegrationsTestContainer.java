package com.example.springtest.integration;

import com.example.springtest.model.Employee;
import com.example.springtest.repository.EmployeeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class EmployeeControlerIntegrationsTestContainer extends AbstractionBaseTest {

    private final MockMvc mockMvc;
    private final EmployeeRepository employeeRepository;
    private final ObjectMapper objectMapper;
    private Employee employee;

    @Autowired
    public EmployeeControlerIntegrationsTestContainer(MockMvc mockMvc, EmployeeRepository employeeRepository, ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.employeeRepository = employeeRepository;
        this.objectMapper = objectMapper;
    }

    @BeforeEach
    void beforeEachTes() {
        employee = Employee.builder().firstName("Atef").lastName("Azouzi").email("atef.azouzi@enis.tn").build();
    }

    @AfterEach
    void setUp() {
        employeeRepository.deleteAll();
    }

    @DisplayName("unit test  for save employee rest controller ..")
    @Test
    public void givenEmployeeObject_whenSaveEmployee_thenReturnEmployeeObject() throws Exception {

        //given - precondition or setup


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
        employeeRepository.saveAll(employees);
        //when  - action or the behavior that we are going to test
        ResultActions perform = mockMvc.perform(get("/api/employees/getAllEmployees"));

        //then  - verify the uot
        perform.andExpect(MockMvcResultMatchers.status().isOk()).andDo(print())
               .andExpect(jsonPath("$.size()", CoreMatchers.is(employees.size())));

    }

    @DisplayName("unit positive test  for getById ..")
    @Test
    public void givenValidEmployeeId_whengetById_thenRerturnEmployee() throws Exception {

        //given - precondition or setup
        employeeRepository.save(employee);
        //when  - action or the behavior that we are going to test
        ResultActions perform = mockMvc.perform(get("/api/employees/getEmployee/{id}", employee.getId()));
        //then  - verify the otput
        perform.andExpect(status().isOk()).andDo(print())
               .andExpect(jsonPath("$.firstName", CoreMatchers.is(employee.getFirstName())))
               .andExpect(jsonPath("$.lastName", CoreMatchers.is(employee.getLastName())))
               .andExpect(jsonPath("$.email", CoreMatchers.is(employee.getEmail())));
    }

    @DisplayName("unit negative test  for getById ..")
    @Test
    public void givenInvalidEmployeeId_whengetById_thenRerturnNotFound() throws Exception {
        long employyeId = 11234l;
        //when  - action or the behavior that we are going to test
        ResultActions perform = mockMvc.perform(get("/api/employees/getEmployee/{id}", employyeId));
        //then  - verify the uotput
        perform.andExpect(status().isNotFound()).andDo(print());
    }

    @DisplayName("unit test for update employee controler ..")
    @Test
    public void givenIdAndEmployeeObject_whenSaveEmployee_thenReturnSavedEmployeeAndResponseOk() throws Exception {

        //given - precondition or setup
        employeeRepository.save(employee);
        Employee employeeToUpdate = Employee.builder().firstName("AtefUo").lastName("AZouziUp").build();

        //when  - action or the behavior that we are going to test
        ResultActions response = mockMvc.perform(put("/api/employees//update/{id}", employee.getId()).contentType(MediaType.APPLICATION_JSON)
                                                                                                     .content(objectMapper.writeValueAsString(employeeToUpdate)));
        //then  - verify the uotput
        response.andExpect(status().isOk()).andDo(print())
                .andExpect(jsonPath("$.firstName", CoreMatchers.is(employeeToUpdate.getFirstName())))
                .andExpect(jsonPath("$.lastName", CoreMatchers.is(employeeToUpdate.getLastName())));
    }

    @DisplayName("test for mysql container")
    @Test
    public void testIfMySqlRun() {
        assertThat(MY_SQL_CONTAINER.isRunning()).isTrue();
    }
}
