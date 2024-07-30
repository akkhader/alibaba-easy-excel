package akkhader.alibaba_easy_excel.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UploadErrorResponse {

    private Integer errorRow;

    private List<String> errorMsg;

}
