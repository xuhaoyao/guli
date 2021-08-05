package com.scnu.vod.controller;

import com.scnu.utils.Result;
import com.scnu.vod.service.VodService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Api(tags = "视频管理")
@RestController
@RequestMapping("/eduvod/video")
@CrossOrigin
public class VodController {

    @Autowired
    private VodService vodService;

    /*
org.apache.tomcat.util.http.fileupload.FileUploadBase$FileSizeLimitExceededException: The field file exceeds its maximum permitted size of 1048576 bytes.
默认上传大小是1M
需要在application.properties中修改上传大小
     */
    @ApiOperation("上传视频到阿里云")
    @PostMapping
    public Result uploadVideo(MultipartFile file){
        String videoId = vodService.uploadVideo(file);
        return Result.ok().data("videoId",videoId);
    }

    @ApiOperation("根据id删除一个云端视频")
    @DeleteMapping("/{id}")
    public Result deleteVideo(@PathVariable("id") String id){
        vodService.deleteVideo(id);
        return Result.ok();
    }

    @ApiOperation("批量删除云端视频")
    @DeleteMapping("/deleteBatch")
    public Result deleteVideoBatch(@RequestParam("ids") List<String> ids){
        vodService.deleteVideoBatch(ids);
        return Result.ok();
    }

}
