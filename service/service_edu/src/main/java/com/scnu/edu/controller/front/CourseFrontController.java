package com.scnu.edu.controller.front;

import com.scnu.edu.client.OrderClient;
import com.scnu.edu.entity.Course;
import com.scnu.edu.service.ChapterService;
import com.scnu.edu.service.CourseService;
import com.scnu.edu.vo.ChapterVo;
import com.scnu.edu.vo.CourseFrontQuery;
import com.scnu.edu.vo.CourseInfoFrontVo;
import com.scnu.edu.vo.TeacherQuery;
import com.scnu.utils.JwtUtils;
import com.scnu.utils.Result;
import com.scnu.utils.dto.OrderCourseInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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

    @Autowired
    private OrderClient orderClient;

    @ApiOperation("前台课程页面的条件分页查询")
    @PostMapping("/page/{current}/{size}")
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

    @ApiOperation("根据课程id查询前台课程详细页面数据")
    @GetMapping("/{id}")
    public Result getCourseInfo(@PathVariable("id") String id, HttpServletRequest request){
        CourseInfoFrontVo courseInfoFrontVo = courseService.getCourseInfoFront(id);
        List<ChapterVo> allChapterVos = chapterService.getAllChapterVos(id);
        boolean login = JwtUtils.checkToken(request);
        boolean isBuy = false;
        if(login){
            isBuy = orderClient.isBuyCourse(id,JwtUtils.getMemberIdByJwtToken(request));
        }
        return Result.ok().data("courseInfo",courseInfoFrontVo).data("chapterVideoInfo",allChapterVos).data("isBuy",isBuy);
    }

    @ApiOperation("生成订单的课程信息")
    @GetMapping("/orderInfo/{courseId}")
    public OrderCourseInfo orderCourseInfo(@PathVariable("courseId") String courseId){
        return courseService.getOrderCourseInfo(courseId);
    }

    @ApiOperation("订单购买成功后,更新课程表")
    @PutMapping("/orderbuy/{courseId}")
    public Result orderBuy(@PathVariable("courseId") String courseId){
        courseService.orderBuy(courseId);
        return Result.ok();
    }

    @ApiOperation("课程浏览数增加")
    @PutMapping("/viewCount/{courseId}")
    public Result addVidwCount(@PathVariable("courseId") String courseId){
        courseService.addVidwCount(courseId);
        return Result.ok();
    }
}
