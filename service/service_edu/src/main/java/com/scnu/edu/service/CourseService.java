package com.scnu.edu.service;

import com.scnu.edu.entity.Course;
import com.baomidou.mybatisplus.extension.service.IService;
import com.scnu.edu.vo.CourseInfoVo;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author xhy
 * @since 2021-08-02
 */
public interface CourseService extends IService<Course> {

    void addCourseInfo(CourseInfoVo courseInfoVo);
}
