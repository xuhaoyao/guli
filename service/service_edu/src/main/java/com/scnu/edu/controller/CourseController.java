package com.scnu.edu.controller;


import com.scnu.edu.service.CourseService;
import com.scnu.edu.vo.CourseInfoVo;
import com.scnu.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author xhy
 * @since 2021-08-02
 */
@Api(tags = "课程管理")
@RestController
@RequestMapping("/eduservice/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @ApiOperation("新增课程")
    @PostMapping
    public Result addCourseInfo(
            @ApiParam(name = "courseInfoVo",value = "课程基本信息", required = true)
            @RequestBody CourseInfoVo courseInfoVo){
        courseService.addCourseInfo(courseInfoVo);
        return Result.ok();
    }

}

