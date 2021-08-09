package com.scnu.edu.client;

import com.scnu.edu.client.impl.VodFileDegradeFeignClient;
import com.scnu.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

//@FeignClient注解用于指定从哪个服务中调用功能,名称与被调用的服务名保持一致.
@FeignClient(value = "service-vod",fallback = VodFileDegradeFeignClient.class)
@Component
public interface VodClient {
    //定义调用的方法路径(路径一定要写全)
    @DeleteMapping("/eduvod/video/{id}")
    Result deleteVideo(@PathVariable("id") String id);

    @DeleteMapping("/eduvod/video/deleteBatch")
    Result deleteVideoBatch(@RequestParam("ids") List<String> ids);
}
