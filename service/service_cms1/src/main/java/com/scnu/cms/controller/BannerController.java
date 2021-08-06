package com.scnu.cms.controller;


import com.scnu.cms.entity.Banner;
import com.scnu.cms.service.BannerService;
import com.scnu.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author xhy
 * @since 2021-08-06
 */
@Api(tags = "banner展示")
@RestController
@RequestMapping("/educms/banner")
@CrossOrigin
public class BannerController {

    @Autowired
    private BannerService bannerService;

    @ApiOperation("取最近修改的两条的banner")
    @GetMapping("/getAllBanner")
    public Result getAllBanner(){
        List<Banner> bannerList = bannerService.getAllBanner();
        return Result.ok().data("items",bannerList);
    }

    @ApiOperation("添加banner")
    @PostMapping
    public Result saveBanner(@RequestBody Banner banner){
        bannerService.saveBanner(banner);
        return Result.ok();
    }

    @ApiOperation("更新banner")
    @PutMapping
    public Result updateBanner(@RequestBody Banner banner){
        bannerService.updateBanner(banner);
        return Result.ok();
    }

    @ApiOperation("删除Banner")
    @DeleteMapping("/{id}")
    public Result deleteBanner(@PathVariable("id") String id){
        bannerService.deleteBanner(id);
        return Result.ok();
    }

}

