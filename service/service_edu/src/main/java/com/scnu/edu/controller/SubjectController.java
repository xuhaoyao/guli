package com.scnu.edu.controller;


import com.scnu.edu.service.SubjectService;
import com.scnu.edu.vo.FirstSubject;
import com.scnu.exceptions.SubjectException;
import com.scnu.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author xhy
 * @since 2021-08-02
 */
@Api(tags = "课程分类管理")
@RestController
@RequestMapping("/eduservice/subject")
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    @ApiOperation("Excel批量导入")
    @PostMapping
    public Result addSubject(MultipartFile file){
        boolean flag = subjectService.addSubject(file);
        if(flag){
            return Result.ok();
        }
        else{
            return Result.error();
        }
    }

    @ApiOperation("展示课程分类树形结构(两级分类)")
    @GetMapping
    public Result getSubjectTree(){
        List<FirstSubject> subjects = subjectService.getTreeList();
        return Result.ok().data("items",subjects);
    }

}

