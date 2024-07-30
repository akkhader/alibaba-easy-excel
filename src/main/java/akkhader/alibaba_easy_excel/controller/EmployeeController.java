package akkhader.alibaba_easy_excel.controller;

import akkhader.alibaba_easy_excel.EmployeeService;
import akkhader.alibaba_easy_excel.excel.EmployeeExcel;
import akkhader.alibaba_easy_excel.response.UploadErrorResponse;
import com.alibaba.excel.EasyExcel;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author Khader
 */

@RestController
@RequestMapping(path="api/v1/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;



    @GetMapping(value = "/download")
    public void download(HttpServletResponse response) throws IOException {
        String fileName = "hub.xlsx";
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        //.useDefaultStyle(false)
        EasyExcel.write(response.getOutputStream(), EmployeeExcel.class).sheet().doWrite(employeeService.fetchEmployees());
    }



    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public ResponseEntity<?> upload(@RequestPart("file") MultipartFile file) throws IOException {
        List<EmployeeExcel> bulkUploadList = EasyExcel.read(file.getInputStream()).head(EmployeeExcel.class).sheet().doReadSync();
        List<UploadErrorResponse> validation = employeeService.validateExcel(bulkUploadList);
        if (!CollectionUtils.isEmpty(validation)) {
            return ResponseEntity.badRequest().body(validation);
        }
        ResponseEntity<?> responseEntity = ResponseEntity.ok(employeeService.upload(bulkUploadList));
        return responseEntity;
    }





}
