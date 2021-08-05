package com.scnu.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scnu.edu.entity.Course;
import com.baomidou.mybatisplus.extension.service.IService;
import com.scnu.edu.vo.CourseInfoVo;
import com.scnu.edu.vo.CoursePublishVo;
import com.scnu.edu.vo.CourseQuery;

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
}
