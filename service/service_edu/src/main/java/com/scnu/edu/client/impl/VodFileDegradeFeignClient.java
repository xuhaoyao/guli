package com.scnu.edu.client.impl;

import com.scnu.edu.client.VodClient;
import com.scnu.utils.Result;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VodFileDegradeFeignClient implements VodClient {
    //但vod服务挂掉之后,执行此实现类的方法
    @Override
    public Result deleteVideo(String id) {
        return Result.error();
    }

    @Override
    public Result deleteVideoBatch(List<String> ids) {
        return Result.error();
    }
}
