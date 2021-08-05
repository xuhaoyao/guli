package com.scnu.edu.service;

import com.scnu.edu.entity.Chapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.scnu.edu.vo.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author xhy
 * @since 2021-08-02
 */
public interface ChapterService extends IService<Chapter> {

    List<ChapterVo> getAllChapterVos(String courseId);

    void deleteChapter(String id);

    void deleteByCourseId(String id);
}
