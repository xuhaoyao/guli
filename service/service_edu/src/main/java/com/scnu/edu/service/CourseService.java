package com.scnu.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scnu.edu.entity.Course;
import com.baomidou.mybatisplus.extension.service.IService;
import com.scnu.edu.vo.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author xhy
 * @since 2021-08-02
 */
public interface CourseService extends IService<Course> {

    String addCourseInfo(CourseInfoVo courseInfoVo);

    CourseInfoVo getCourseInfoById(String id);

    void updateCourseInfo(CourseInfoVo courseInfoVo);

    CoursePublishVo getCoursePublish(String id);

    void publishCourse(String id);

    Page<Course> getPageByCondition(Integer current, Integer size, CourseQuery courseQuery);

    void deleteCourse(String id);

    List<Course> getHotCourseList(Integer num);

    List<Course> getTeacherCourses(String id);

    Map<String, Object> courseFrontInfo(Integer current, Integer size, CourseFrontQuery courseFrontQuery);

    CourseInfoFrontVo getCourseInfoFront(String id);
}
