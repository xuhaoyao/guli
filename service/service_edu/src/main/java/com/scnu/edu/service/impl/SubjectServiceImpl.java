package com.scnu.edu.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.scnu.edu.entity.Subject;
import com.scnu.edu.excel.SubjectData;
import com.scnu.edu.listener.ExcelListener;
import com.scnu.edu.mapper.SubjectMapper;
import com.scnu.edu.service.SubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scnu.edu.vo.FirstSubject;
import com.scnu.edu.vo.SecondSubject;
import com.scnu.exceptions.SubjectException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author xhy
 * @since 2021-08-02
 */
@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements SubjectService {

    @Transactional(rollbackFor = SubjectException.class)
    @Override
    public boolean addSubject(MultipartFile file) {
        if(file == null){
            return false;
        }
        try {
            EasyExcel.read(file.getInputStream(), SubjectData.class,new ExcelListener(this)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
            throw new SubjectException("添加课程分类失败");
        }
        return true;
    }

    @Override
    public Subject getFirst(String firstName) {
        QueryWrapper<Subject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",firstName);
        wrapper.eq("parent_id","0");
        return baseMapper.selectOne(wrapper);
    }

    @Override
    public Subject getSecond(String secondName, String parentId) {
        QueryWrapper<Subject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",secondName);
        wrapper.eq("parent_id",parentId);
        return baseMapper.selectOne(wrapper);
    }

    @Override
    public List<FirstSubject> getTreeList() {
        //1.查一级分类
        List<Subject> firstSubjects = getFirstSubjects();
        //2.查二级分类
        List<Subject> secondSubjects = getSecondSubjects();

        //3.封装结果
        List<FirstSubject> ans = new ArrayList<>();

        //双重循环遍历,在二级分类中,若parent_id与当前循环的一级分类id一致,那么添加进children集合
        for (Subject firstSubject : firstSubjects) {
            FirstSubject fs = new FirstSubject();

            String id = firstSubject.getId();
            fs.setId(id);
            fs.setTitle(firstSubject.getTitle());

            List<SecondSubject> children = new ArrayList<>();
            for (Subject secondSubject : secondSubjects) {
                if(secondSubject.getParentId().equals(id)){
                    SecondSubject ss = new SecondSubject();
                    ss.setId(secondSubject.getId());
                    ss.setTitle(secondSubject.getTitle());
                    children.add(ss);
                }
            }
            fs.setChildren(children);
            ans.add(fs);
        }
        return ans;
    }

    private List<Subject> getSecondSubjects() {
        QueryWrapper<Subject> wrapper = new QueryWrapper<>();
        wrapper.ne("parent_id","0");
        wrapper.orderByDesc("sort");
        return baseMapper.selectList(wrapper);
    }

    private List<Subject> getFirstSubjects() {
        QueryWrapper<Subject> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id","0");
        wrapper.orderByDesc("sort");
        return baseMapper.selectList(wrapper);
    }
}
