package com.scnu.edu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.scnu.edu.entity.Course;
import com.scnu.edu.service.CourseService;
import com.scnu.edu.vo.CourseInfoVo;
import com.scnu.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@CrossOrigin
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


}

