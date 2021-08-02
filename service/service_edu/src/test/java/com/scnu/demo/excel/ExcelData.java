package com.scnu.demo.excel;


import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

//设置表头和添加的数据字段
@Data
public class ExcelData {
    //设置表头名称
    @ExcelProperty(index = 0)
    private int sno;

    //设置表头名称
    @ExcelProperty(index = 1)
    private String sname;

    @ExcelProperty(index = 2)
    private String email;

}