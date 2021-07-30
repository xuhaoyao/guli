package com.scnu.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scnu.edu.entity.Teacher;
import com.scnu.edu.mapper.TeacherMapper;
import com.scnu.edu.service.TeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scnu.edu.vo.TeacherQuery;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.sql.Wrapper;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author xhy
 * @since 2021-07-30
 */
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {

    public Page<Teacher> getPageByCondition(Integer current, Integer size, TeacherQuery teacherQuery){
        Page<Teacher> page = new Page<>(current,size);
        QueryWrapper<Teacher> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("sort");
        if(teacherQuery == null){
            ;
        }
        else {
            String name = teacherQuery.getName();
            Integer level = teacherQuery.getLevel();
            String begin = teacherQuery.getBegin();
            String end = teacherQuery.getEnd();
            if (StringUtils.hasLength(name)) {
                wrapper.like("name",name);
            }
            if(level != null) {
                wrapper.eq("level", level);
            }
            if(StringUtils.hasLength(begin)){
                wrapper.ge("gmt_create",begin);
            }
            if(StringUtils.hasLength(end)){
                wrapper.le("gmt_modified",end);
            }
        }
        baseMapper.selectPage(page,wrapper);
        return page;
    }

}
