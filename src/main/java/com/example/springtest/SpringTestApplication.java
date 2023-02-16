package com.example.springtest;

import com.example.springtest.model.Employee;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringTestApplication {

    public static void main(String[] args) {
        Employee employee = Employee.
                builder()
                .lastName("azouzi")
                .firstName("azouzi")
                .email("atef.azouzi@enis.tn")
                .build();


        SpringApplication.run(SpringTestApplication.class, args);
    }

}
