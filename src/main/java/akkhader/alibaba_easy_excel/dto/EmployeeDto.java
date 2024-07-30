package akkhader.alibaba_easy_excel.dto;

import akkhader.alibaba_easy_excel.entity.Employee;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link Employee}
 */
@Value
public class EmployeeDto implements Serializable {
    Long id;
    String name;
    String email;
    String department;
    Double salary;
}