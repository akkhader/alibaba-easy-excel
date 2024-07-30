package akkhader.alibaba_easy_excel.repository;

import akkhader.alibaba_easy_excel.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}