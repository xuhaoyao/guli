package com.scnu.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scnu.edu.entity.Teacher;
import com.baomidou.mybatisplus.extension.service.IService;
import com.scnu.edu.vo.TeacherQuery;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author xhy
 * @since 2021-07-30
 */
public interface TeacherService extends IService<Teacher> {

    Page<Teacher> getPageByCondition(Integer current, Integer size, TeacherQuery teacherQuery);
}
