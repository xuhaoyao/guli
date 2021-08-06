package com.scnu.edu.controller.front;

import com.scnu.edu.entity.Course;
import com.scnu.edu.entity.Teacher;
import com.scnu.edu.service.CourseService;
import com.scnu.edu.service.TeacherService;
import com.scnu.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "前台展示")
@RestController
@RequestMapping("/eduservice/index")
@CrossOrigin
public class IndexController {

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private CourseService courseService;

    @ApiOperation("给前台显示8条热门课程和4位名师")
    @GetMapping("/index")
    public Result index(){
        List<Course> courses = courseService.getHotCourseList(8);
        List<Teacher> teachers = teacherService.getGoodTeacherList(4);
        return Result.ok().data("courseList",courses).data("teacherList",teachers);
    }

}
