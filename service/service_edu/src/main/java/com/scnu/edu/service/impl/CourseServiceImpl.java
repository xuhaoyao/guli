package com.scnu.edu.service.impl;

import com.scnu.edu.entity.Course;
import com.scnu.edu.entity.CourseDescription;
import com.scnu.edu.mapper.CourseMapper;
import com.scnu.edu.service.CourseDescriptionService;
import com.scnu.edu.service.CourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scnu.edu.vo.CourseInfoVo;
import com.scnu.exceptions.CourseException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author xhy
 * @since 2021-08-02
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

    @Autowired
    private CourseDescriptionService courseDescriptionService;

    @Transactional(rollbackFor = CourseException.class)
    @Override
    public void addCourseInfo(CourseInfoVo courseInfoVo) {

        //1.edu_course
        Course course = new Course();
        //偷懒写法,这里必须要保证对应字段的名称一模一样
        BeanUtils.copyProperties(courseInfoVo,course);
        int result = baseMapper.insert(course);
        if(result != 1){
            throw new CourseException("添加课程信息失败");
        }

        //此处course表和course_description是一对一关系,利用两张表的主键(相同)建立外键关联
        String id = course.getId();
        //2.edu_course_description
        CourseDescription cd = new CourseDescription();
        cd.setDescription(courseInfoVo.getDescription());
        cd.setId(id);
        boolean save = courseDescriptionService.save(cd);
        if(!save){
            throw new CourseException("添加课程信息成功后,添加对应的课程描述失败");
        }
    }
}
