package com.scnu.edu.controller.front;

import com.scnu.edu.entity.Course;
import com.scnu.edu.service.ChapterService;
import com.scnu.edu.service.CourseService;
import com.scnu.edu.vo.ChapterVo;
import com.scnu.edu.vo.CourseFrontQuery;
import com.scnu.edu.vo.CourseInfoFrontVo;
import com.scnu.edu.vo.TeacherQuery;
import com.scnu.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Api(tags = "前台课程管理")
@RestController
@RequestMapping("/eduservice/coursefront")
@CrossOrigin
public class CourseFrontController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private ChapterService chapterService;

    @ApiOperation("前台课程页面的条件分页查询")
    @PostMapping("/{current}/{size}")
    public Result getCoursePageByCondition(
            @ApiParam(name = "current",value = "当前页码",required = true)
            @PathVariable("current") Integer current,

            @ApiParam(name = "size",value = "每页记录数",required = true)
            @PathVariable("size") Integer size,

            @ApiParam(name = "courseFrontQuery",value = "表单提交的查询数据",required = false)
                    @RequestBody CourseFrontQuery courseFrontQuery){
        Map<String,Object> map  = courseService.courseFrontInfo(current,size,courseFrontQuery);
        return Result.ok().data(map);
    }

    @ApiOperation("前台课程详细页面数据")
    @GetMapping("/{id}")
    public Result getCourseInfo(@PathVariable("id") String id){
        CourseInfoFrontVo courseInfoFrontVo = courseService.getCourseInfoFront(id);
        List<ChapterVo> allChapterVos = chapterService.getAllChapterVos(id);
        return Result.ok().data("courseInfo",courseInfoFrontVo).data("chapterVideoInfo",allChapterVos);
    }
}
