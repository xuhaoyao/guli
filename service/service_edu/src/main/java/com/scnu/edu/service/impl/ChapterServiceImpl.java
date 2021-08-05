package com.scnu.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.scnu.edu.entity.Chapter;
import com.scnu.edu.entity.Video;
import com.scnu.edu.mapper.ChapterMapper;
import com.scnu.edu.service.ChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scnu.edu.service.VideoService;
import com.scnu.edu.vo.ChapterVo;
import com.scnu.edu.vo.VideoVo;
import com.scnu.exceptions.ChapterException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author xhy
 * @since 2021-08-02
 */
@Service
public class ChapterServiceImpl extends ServiceImpl<ChapterMapper, Chapter> implements ChapterService {

    @Autowired
    private VideoService videoService;

    @Override
    public List<ChapterVo> getAllChapterVos(String courseId) {

        //1.先根据课程id查所有章节
        List<Chapter> chapters = getAllChaptersByCourseId(courseId);
        //2.再根据课程id查所有的小节视频
        List<Video> videos = getAllVideosByCourseId(courseId);
        //汇总返回
        List<ChapterVo> chapterVos = new ArrayList<>(chapters.size());

        for (Chapter chapter : chapters) {
            ChapterVo chapterVo = new ChapterVo();
            //偷懒写法:必须保证对应字段名一致才能用
            BeanUtils.copyProperties(chapter, chapterVo);

            String chapterId = chapter.getId();
            List<VideoVo> children = new ArrayList<>();

            //找此章节下的小节视频
            for (Video video : videos) {
                if(chapterId.equals(video.getChapterId())){
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(video,videoVo);
                    children.add(videoVo);
                }
            }
            chapterVo.setChildren(children);
            chapterVos.add(chapterVo);
        }

        return chapterVos;
    }

    private List<Video> getAllVideosByCourseId(String courseId) {
        QueryWrapper<Video> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        wrapper.orderByAsc("sort");
        return videoService.list(wrapper);
    }

    private List<Chapter> getAllChaptersByCourseId(String courseId) {
        QueryWrapper<Chapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        wrapper.orderByAsc("sort");
        return baseMapper.selectList(wrapper);
    }

    @Override
    public void deleteChapter(String id) {
        QueryWrapper<Video> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id",id);
        int count = videoService.count(wrapper);
        if(count > 0){
            throw new ChapterException("章节下还有小节,禁止删除");
        }
        else{
            //若章节下没有小节,删除该章节
            baseMapper.deleteById(id);
        }
    }

    @Override
    public void deleteByCourseId(String id) {
        QueryWrapper<Chapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",id);
        baseMapper.delete(wrapper);
    }
}
