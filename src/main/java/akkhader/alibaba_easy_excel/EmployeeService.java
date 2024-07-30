package akkhader.alibaba_easy_excel;

import akkhader.alibaba_easy_excel.entity.Employee;
import akkhader.alibaba_easy_excel.excel.EmployeeExcel;
import akkhader.alibaba_easy_excel.repository.EmployeeRepository;
import akkhader.alibaba_easy_excel.response.UploadErrorResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Khader
 */
@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    private static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    public static <T> Set<ConstraintViolation<T>> validate(T object) {
        return validator.validate(object);
    }

    public List<Employee> fetchEmployees(){
       return employeeRepository.findAll();
    }

    public  List<Employee> download() {
        return employeeRepository.findAll();
    }

    public List<UploadErrorResponse> validateExcel(List<EmployeeExcel> dataList) {
        List<UploadErrorResponse> errorList = new ArrayList<>();
        for (int i = 0; i < dataList.size(); i++) {
            Set<ConstraintViolation<EmployeeExcel>> violations = validate(dataList.get(i));
            List<String> errorMsg = new ArrayList<>();
            if (!violations.isEmpty()) {
                errorMsg = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
            }
            if (!CollectionUtils.isEmpty(errorMsg)) {
                errorList.add(new UploadErrorResponse((i + 2), errorMsg));
            }
        }
        return errorList;
    }


    public List<Employee> upload(List<EmployeeExcel> bulkUploadList) {

        if (CollectionUtils.isEmpty(bulkUploadList)) {
            return null;
        }
        List<Employee> employees = convertExcelToHub(bulkUploadList);
       return employeeRepository.saveAll(employees);
    }

    private List<Employee> convertExcelToHub(List<EmployeeExcel> source) {
        return source.stream().map(t -> mapEmployee(t)).collect(Collectors.toList());
    }

    private Employee mapEmployee(EmployeeExcel t) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(t,Employee.class);
    }

}
