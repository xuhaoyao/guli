package com.scnu.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.scnu.edu.client.VodClient;
import com.scnu.edu.entity.Video;
import com.scnu.edu.mapper.VideoMapper;
import com.scnu.edu.service.VideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scnu.exceptions.VideoException;
import com.scnu.exceptions.VodException;
import com.scnu.utils.Code;
import com.scnu.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author xhy
 * @since 2021-08-02
 */
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements VideoService {

    @Autowired
    private VodClient vodClient;

    @Transactional
    @Override
    public void deleteByCourseId(String id) {
        QueryWrapper<Video> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",id);
        wrapper.select("video_source_id");
        List<Video> videos = baseMapper.selectList(wrapper);
        List<String> ids = new ArrayList<>(videos.size());
        //取出视频id
        for (Video video : videos) {
            //此处由于会没有视频,查出来的video可能为null
            if(video == null){
                continue;
            }
            String videoSourceId = video.getVideoSourceId();
            if (StringUtils.hasLength(videoSourceId)) {
                ids.add(videoSourceId);
            }
        }
        if(ids.size() > 0){
            Result result = vodClient.deleteVideoBatch(ids);
            if(result.getCode() == Code.ERROR){
                throw new VodException("Vod服务异常,批量删除云端视频失败");
            }
        }

        //删除完云端视频之后,再将小节部分删除
        QueryWrapper<Video> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("course_id",id);
        int result = baseMapper.delete(wrapper1);
        if (result != videos.size()){
            throw new VideoException("删除小节失败!");
        }

    }

    @Transactional
    @Override
    public void deleteVideo(String id) {
        Video video = baseMapper.selectById(id);
        String videoSourceId = video.getVideoSourceId();
        if(StringUtils.hasLength(videoSourceId)){
            //如果存在视频资源,则删除
            Result result = vodClient.deleteVideo(videoSourceId);
            if(result.getCode() == Code.ERROR){
                throw new VodException("Vod服务异常,视频删除失败");
            }
        }
        int result = baseMapper.deleteById(id);
        if(result != 1){
            throw new VideoException("删除小节失败!");
        }
    }
}
