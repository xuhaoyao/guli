package com.scnu.oss.controller;

import com.scnu.oss.service.OssService;
import com.scnu.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Api(tags = "阿里云文件管理(OSS)")
@RestController
@RequestMapping("/eduoss/file")
@CrossOrigin
public class OssController {

    @Autowired
    OssService ossService;

    @ApiOperation("文件上传")
    @PostMapping("/upload")
    public Result uploadFile(MultipartFile file){
        /*
            field="file"
            前端中field的值是什么,此处参数名称就要对应,否则接受不到
            类似于<input type="file" name="file"/>
         */
        String url = ossService.upload(file);
        return Result.ok().data("url",url);
    }

}
