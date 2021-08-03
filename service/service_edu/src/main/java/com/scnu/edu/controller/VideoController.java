package com.scnu.edu.controller;


import com.scnu.edu.entity.Video;
import com.scnu.edu.service.VideoService;
import com.scnu.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author xhy
 * @since 2021-08-02
 */
@Api(tags = "小节管理")
@RestController
@RequestMapping("/eduservice/video")
@CrossOrigin
public class VideoController {

    @Autowired
    private VideoService videoService;

    @ApiOperation("添加小节")
    @PostMapping
    public Result addVideo(@RequestBody Video video){
        videoService.save(video);
        return Result.ok();
    }

    @ApiOperation("更新小节")
    @PutMapping
    public Result updateVideo(@RequestBody Video video){
        videoService.updateById(video);
        return Result.ok();
    }

    @ApiOperation("通过id查询小节")
    @GetMapping("/{id}")
    public Result getVideo(@PathVariable("id") String id){
        Video video = videoService.getById(id);
        return Result.ok().data("item",video);
    }

    //待完善
    @ApiOperation("通过id删除小节")
    @DeleteMapping("/{id}")
    public Result deleteVideo(@PathVariable("id") String id){
        videoService.removeById(id);
        return Result.ok();
    }

}

