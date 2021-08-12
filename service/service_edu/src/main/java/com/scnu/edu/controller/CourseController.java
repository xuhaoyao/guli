package com.scnu.edu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scnu.edu.entity.Course;
import com.scnu.edu.entity.Teacher;
import com.scnu.edu.service.CourseService;
import com.scnu.edu.vo.CourseInfoVo;
import com.scnu.edu.vo.CoursePublishVo;
import com.scnu.edu.vo.CourseQuery;
import com.scnu.edu.vo.TeacherQuery;
import com.scnu.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        String id = courseService.addCourseInfo(courseInfoVo);
        return Result.ok().data("id",id);
    }

    //在路径变量中的值id,也可以对应的赋值给CourseInfoVo的id
    //此处的第二个参数String id 多余了,但是不改了,放着看看
    @ApiOperation("更新课程")
    @PutMapping("/{id}")
    public Result updateCourseInfo(
            @ApiParam(name = "courseInfoVo",value = "课程基本信息", required = true)
            @RequestBody CourseInfoVo courseInfoVo,
            @ApiParam(name = "id",value = "课程id",required = true)
            @PathVariable("id") String id){
        courseInfoVo.setId(id);
        courseService.updateCourseInfo(courseInfoVo);
        return Result.ok();
    }

    @ApiOperation("根据id查对应课程信息")
    @GetMapping("/{id}")
    public Result getCourseInfoById(@PathVariable("id") String id){
        CourseInfoVo courseInfoVo = courseService.getCourseInfoById(id);
        return Result.ok().data("item",courseInfoVo);
    }

    @ApiOperation("根据课程id查询最终课程发布的信息")
    @GetMapping("/coursePublish/{id}")
    public Result getCoursePublish(@PathVariable("id") String id){
        CoursePublishVo coursePublishVo = courseService.getCoursePublish(id);
        return Result.ok().data("item",coursePublishVo);
    }

    @ApiOperation("课程最终发布,根据id修改课程的状态为Normal,表示发布")
    @PutMapping("/publish/{id}")
    public Result publishCourse(@PathVariable("id") String id){
        courseService.publishCourse(id);
        return Result.ok();
    }

    @ApiOperation("按照条件分页查询")
    @GetMapping("/{current}/{size}")
    public Result queryCondition(
            @ApiParam(name = "current",value = "当前页码",required = true)
            @PathVariable("current") Integer current,

            @ApiParam(name = "size",value = "每页记录数",required = true)
            @PathVariable("size") Integer size,

            @ApiParam(name = "courseQuery",value = "表单提交的查询数据",required = false)
                    CourseQuery courseQuery){
        Page<Course> teacherPage = courseService.getPageByCondition(current,size,courseQuery);
        Long total = teacherPage.getTotal();
        List<Course> records = teacherPage.getRecords();
        return Result.ok().data("total",total).data("records",records);
    }

    @ApiOperation("根据id删除课程")
    @DeleteMapping("/{id}")
    public Result deleteCourse(@PathVariable("id") String id){
        courseService.deleteCourse(id);
        return Result.ok();
    }

}

