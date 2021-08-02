package com.scnu.edu.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubjectData {

    @ExcelProperty(index = 0)
    private String firstName;

    @ExcelProperty(index = 1)
    private String secondName;

}
