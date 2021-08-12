package com.scnu.edu.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scnu.edu.entity.Teacher;
import com.scnu.edu.service.TeacherService;
import com.scnu.edu.vo.TeacherQuery;
import com.scnu.exceptions.TeacherException;
import com.scnu.swagger.config.SwaggerConfig;
import com.scnu.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author xhy
 * @since 2021-07-30
 */
@Api(tags = "后台讲师管理")
@RestController
@RequestMapping("/eduservice/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @ApiOperation("查询所有讲师")
    @GetMapping("/findAll")
    public Result findAll(){
        List<Teacher> teachers = teacherService.list(null);
        return Result.ok().data("items",teachers);
    }

    @ApiOperation("按id删除一个讲师")
    @DeleteMapping(value = "/{id}")
    public Result deleteTeacher(@PathVariable("id") String id){
        boolean flag =  teacherService.removeById(id);
        if(flag){
            return Result.ok();
        }
        else{
            throw new TeacherException("删除讲师失败");
        }
    }

    @ApiOperation("按照条件分页查询")
    @GetMapping("/{current}/{size}")
    public Result queryCondition(
            @ApiParam(name = "current",value = "当前页码",required = true)
            @PathVariable("current") Integer current,

            @ApiParam(name = "size",value = "每页记录数",required = true)
            @PathVariable("size") Integer size,

            @ApiParam(name = "teacherQuery",value = "表单提交的查询数据",required = false)
            TeacherQuery teacherQuery){
        Page<Teacher> teacherPage = teacherService.getPageByCondition(current,size,teacherQuery);
        Long total = teacherPage.getTotal();
        List<Teacher> records = teacherPage.getRecords();
        return Result.ok().data("total",total).data("records",records);
    }

    @ApiOperation("添加一位讲师")
    @PostMapping
    public Result saveTeacher(@RequestBody Teacher teacher){
        boolean flag = teacherService.save(teacher);
        if(flag){
            return Result.ok();
        }
        else{
            throw new TeacherException("添加讲师失败");
        }
    }

    @ApiOperation("查询一位讲师")
    @GetMapping("/{id}")
    public Result getTeacher(@PathVariable("id") String id){
        Teacher teacher = teacherService.getById(id);
        return Result.ok().data("teacher",teacher);
    }

    @ApiOperation("修改一位讲师数据")
    @PutMapping
    public Result modifyTeacher(@RequestBody Teacher teacher){
        boolean flag = teacherService.updateById(teacher);
        if(flag){
            return Result.ok();
        }
        else{
            throw new TeacherException("修改讲师信息失败");
        }
    }


}

