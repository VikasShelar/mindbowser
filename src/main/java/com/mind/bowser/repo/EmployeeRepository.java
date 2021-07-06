package com.mind.bowser.repo;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mind.bowser.entity.Employee;
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {



	

}
