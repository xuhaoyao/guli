package com.scnu.edu.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.scnu.edu.entity.Subject;
import com.scnu.edu.excel.SubjectData;
import com.scnu.edu.service.SubjectService;

public class ExcelListener extends AnalysisEventListener<SubjectData> {

    private SubjectService subjectService;

    public ExcelListener() {
    }

    public ExcelListener(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
        String firstName = subjectData.getFirstName();
        String secondName = subjectData.getSecondName();

        //判断有没有此课程一级分类
        Subject subject = subjectService.getFirst(firstName);
        if(subject == null){
            subject = new Subject();
            subject.setTitle(firstName);
            subject.setParentId("0");
            subjectService.save(subject);
        }

        //判断有没有此课程二级分类
        String parentId = subject.getId();
        Subject subject1 = subjectService.getSecond(secondName,parentId);
        if(subject1 == null){
            subject1 = new Subject();
            subject1.setTitle(secondName);
            subject1.setParentId(parentId);
            subjectService.save(subject1);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
