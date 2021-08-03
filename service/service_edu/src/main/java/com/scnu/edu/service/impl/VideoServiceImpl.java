package com.scnu.edu.service.impl;

import com.scnu.edu.entity.Video;
import com.scnu.edu.mapper.VideoMapper;
import com.scnu.edu.service.VideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scnu.exceptions.VideoException;
import org.springframework.stereotype.Service;

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

}
