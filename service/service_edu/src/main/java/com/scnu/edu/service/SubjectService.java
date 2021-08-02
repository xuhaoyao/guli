package com.scnu.edu.service;

import com.scnu.edu.entity.Subject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.scnu.edu.vo.FirstSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author xhy
 * @since 2021-08-02
 */
public interface SubjectService extends IService<Subject> {

    boolean addSubject(MultipartFile file);

    Subject getFirst(String firstName);

    Subject getSecond(String secondName, String parentId);

    List<FirstSubject> getTreeList();
}
