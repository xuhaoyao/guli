package com.scnu.edu.mapper;

import com.scnu.edu.entity.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.scnu.edu.vo.CourseInfoFrontVo;
import com.scnu.edu.vo.CoursePublishVo;
import com.scnu.utils.dto.OrderCourseInfo;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author xhy
 * @since 2021-08-02
 */
public interface CourseMapper extends BaseMapper<Course> {

    CoursePublishVo getCoursePublishById(String courseId);

    CourseInfoFrontVo getCourseInfoFront(String id);
}
