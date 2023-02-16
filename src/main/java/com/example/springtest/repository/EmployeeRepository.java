package com.example.springtest.repository;

import com.example.springtest.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    public Optional<Employee> findByEmail(String email);

    @Query("select e from Employee e where e.firstName = ?1 and e.lastName = ?2")
    public Employee findByJPQL(String firstName, String lastName);

    @Query(value = "select * from employees e where e.first_name = ?1 and e.last_name = ?2", nativeQuery = true)
    public Employee findByJPQLNative(String firstName, String lastName);

    @Query(value = "select * from employees e where e.first_name =:firstName and e.last_name =:lastName", nativeQuery = true)
    public Employee findByJPQLNativeNamed(@Param("firstName") String firstName, @Param("lastName") String lastName);

}
