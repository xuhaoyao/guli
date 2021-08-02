package com.scnu.demo.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.scnu.edu.entity.Subject;
import com.scnu.edu.excel.SubjectData;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExcelTest {

    @Test
    public void test1(){
        String fileName = "D:\\02.xlsx";
        EasyExcel.write(fileName,SubjectData.class).sheet("课程分类列表").doWrite(data1());
    }

    @Test
    public void test2(){
        String fileName = "D:\\02.xlsx";
        EasyExcel.read(fileName, SubjectData.class, new AnalysisEventListener() {
            @Override
            public void invokeHeadMap(Map headMap, AnalysisContext context) {
                System.out.println(headMap);
            }

            @Override
            public void invoke(Object o, AnalysisContext analysisContext) {
                System.out.println(o);
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext analysisContext) {

            }
        }).sheet().doRead();
    }

    @Test
    public void test3(){
        Subject subject = new Subject();
        System.out.println(subject.getId());
    }

    private List<SubjectData> data1(){
        List<SubjectData> data = new ArrayList<>();
        data.add(new SubjectData("test","Java"));
        data.add(new SubjectData("sdsd","Python"));
        data.add(new SubjectData("ffff","C++"));
        System.out.println(data);
        return data;
    }

    private List<ExcelData> data(){
        List<ExcelData> data = new ArrayList<>();
        for(int i = 0;i < 10;i++){
            ExcelData excelData = new ExcelData();
            excelData.setSno(i);
            excelData.setSname("徐浩耀" + i);
            excelData.setEmail("6223@qq.com");
            data.add(excelData);
        }
        return data;
    }

}
