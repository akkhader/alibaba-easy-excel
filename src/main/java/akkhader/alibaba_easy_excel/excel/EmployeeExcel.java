package akkhader.alibaba_easy_excel.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeExcel {

    @ExcelProperty(value = "Id", index = 0)
    private Long id;

    @ExcelProperty(value = "name", index = 1)
    private String name;
    @ExcelProperty(value = "email", index = 2)
    private String email;
    @ExcelProperty(value = "department", index = 3)
    private String department;
    @ExcelProperty(value = "salary", index = 4)
    private Double salary;




}