package com.scnu.msm.controller;

import com.scnu.msm.service.MsmService;
import com.scnu.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "邮件服务")
@RestController
@RequestMapping("/edumsm/msm")
@CrossOrigin
public class MsmController {

    @Autowired
    private MsmService msmService;


    @ApiOperation("邮件发送验证码")
    @GetMapping("/send/{email}")
    public Result send(@PathVariable("email") String email){
        msmService.send(email);
        return Result.ok();
    }
}
