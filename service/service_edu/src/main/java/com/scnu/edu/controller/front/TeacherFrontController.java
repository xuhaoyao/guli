package com.scnu.edu.controller.front;

import com.scnu.edu.entity.Course;
import com.scnu.edu.entity.Teacher;
import com.scnu.edu.service.CourseService;
import com.scnu.edu.service.TeacherService;
import com.scnu.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Api(tags = "前台讲师管理")
@RestController
@RequestMapping("/eduservice/teacherfront")
@CrossOrigin
public class TeacherFrontController {

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private CourseService courseService;

    @ApiOperation("按照条件分页查询")
    @GetMapping("/{current}/{size}")
    public Result queryCondition(
            @ApiParam(name = "current",value = "当前页码",required = true)
            @PathVariable("current") Integer current,

            @ApiParam(name = "size",value = "每页记录数",required = true)
            @PathVariable("size") Integer size){
        Map<String,Object> teacherPageMap = teacherService.getFrontPage(current,size);
        return Result.ok().data(teacherPageMap);
    }

    @ApiOperation("根据讲师id查询讲师信息和他所讲的课程")
    @GetMapping("/{id}")
    public Result getTeacherInfo(@PathVariable("id") String id){
        Teacher teacher = teacherService.getById(id);
        List<Course> courses = courseService.getTeacherCourses(id);
        return Result.ok().data("teacher",teacher).data("courseList",courses);
    }
}
