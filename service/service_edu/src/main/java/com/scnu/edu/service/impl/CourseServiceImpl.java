package com.scnu.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scnu.edu.entity.Course;
import com.scnu.edu.entity.CourseDescription;
import com.scnu.edu.mapper.CourseMapper;
import com.scnu.edu.service.ChapterService;
import com.scnu.edu.service.CourseDescriptionService;
import com.scnu.edu.service.CourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scnu.edu.service.VideoService;
import com.scnu.edu.vo.CourseInfoVo;
import com.scnu.edu.vo.CoursePublishVo;
import com.scnu.edu.vo.CourseQuery;
import com.scnu.exceptions.CourseException;
import com.scnu.utils.CourseStatus;
import org.junit.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

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

    @Autowired
    private VideoService videoService;

    @Autowired
    private ChapterService chapterService;

    @Transactional(rollbackFor = CourseException.class)
    @Override
    public String addCourseInfo(CourseInfoVo courseInfoVo) {

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
        return id;
    }

    @Override
    public CourseInfoVo getCourseInfoById(String id) {
        //edu_course和edu_course_description是一对一关系,两表主键相同建立外键关联
        //1.查edu_course
        Course course = this.getById(id);
        if(course == null){
            throw new CourseException("课程不存在!");
        }
        //2.查edu_course_description
        CourseDescription courseDescription = courseDescriptionService.getById(id);
        if(courseDescription == null){
            throw new CourseException("课程描述不存在!");
        }
        //3.封装结果
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        BeanUtils.copyProperties(course,courseInfoVo);
        courseInfoVo.setDescription(courseDescription.getDescription());
        return courseInfoVo;
    }

    @Transactional(rollbackFor = CourseException.class)
    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        //edu_course和edu_course_description是一对一关系,两表主键相同建立外键关联
        //1.更新edu_course
        Course course = new Course();
        BeanUtils.copyProperties(courseInfoVo,course);
        int result = baseMapper.updateById(course);
        if(result != 1){
            throw new CourseException("更新课程失败!");
        }
        //2.更新edu_course_description
        CourseDescription courseDescription = new CourseDescription();
        BeanUtils.copyProperties(courseInfoVo,courseDescription);
        boolean result2 = courseDescriptionService.updateById(courseDescription);
        if(!result2){
            throw new CourseException("更新课程成功,但更新课程描述时失败!");
        }
    }

    @Override
    public CoursePublishVo getCoursePublish(String id) {
        return baseMapper.getCoursePublishById(id);
    }

    @Transactional
    @Override
    public void publishCourse(String id) {
        Course course = new Course();
        course.setId(id);
        course.setStatus(CourseStatus.NORMAL);
        baseMapper.updateById(course);
    }

    @Override
    public Page<Course> getPageByCondition(Integer current, Integer size, CourseQuery courseQuery) {
        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        Page<Course> coursePage = new Page<>(current,size);
        if(courseQuery != null) {
            String status = courseQuery.getStatus();
            String subjectId = courseQuery.getSubjectId();
            String subjectParentId = courseQuery.getSubjectParentId();
            String title = courseQuery.getTitle();
            String teacherId = courseQuery.getTeacherId();
            if (StringUtils.hasLength(teacherId)) {
                wrapper.eq("teacher_id", teacherId);
            }
            if (StringUtils.hasLength(subjectId)) {
                wrapper.eq("subject_id", subjectId);
            }
            if (StringUtils.hasLength(subjectParentId)) {
                wrapper.eq("subject_parent_id", subjectParentId);
            }
            if (StringUtils.hasLength(status)) {
                wrapper.eq("status", status);
            }
            if (StringUtils.hasLength(title)) {
                wrapper.like("title", title);
            }
        }
        wrapper.orderByDesc("gmt_create");
        baseMapper.selectPage(coursePage,wrapper);
        return coursePage;
    }

    @Transactional
    @Override
    public void deleteCourse(String id) {
        //1.删除小节
        videoService.deleteByCourseId(id);
        //2.删除章节
        chapterService.deleteByCourseId(id);
        //3.删除课程简介
        courseDescriptionService.removeById(id);
        //4.删除该课程
        int result = baseMapper.deleteById(id);
        if(result != 1){
            throw new CourseException("删除课程失败");
        }
    }
}
