package com.scnu.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scnu.edu.entity.Teacher;
import com.scnu.edu.mapper.TeacherMapper;
import com.scnu.edu.service.TeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scnu.edu.vo.TeacherQuery;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.sql.Wrapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Override
    public List<Teacher> getGoodTeacherList(int i) {
        QueryWrapper<Teacher> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("level","sort").last("limit " + i);
        return baseMapper.selectList(wrapper);
    }

    //分页查询名师
    @Override
    public Map<String, Object> getFrontPage(Integer _current, Integer _size) {
        QueryWrapper<Teacher> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("level", "sort");
        Page<Teacher> pageParam = new Page<>(_current,_size);
        baseMapper.selectPage(pageParam,wrapper);
        List<Teacher> records = pageParam.getRecords();
        long current = pageParam.getCurrent();
        long pages = pageParam.getPages();
        long size = pageParam.getSize();
        long total = pageParam.getTotal();
        boolean hasNext = pageParam.hasNext();
        boolean hasPrevious = pageParam.hasPrevious();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);
        return map;
    }

}
