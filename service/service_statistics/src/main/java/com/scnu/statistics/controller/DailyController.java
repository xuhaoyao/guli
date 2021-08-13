package com.scnu.statistics.controller;


import com.scnu.statistics.service.DailyService;
import com.scnu.statistics.vo.ChartConditionVo;
import com.scnu.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author xhy
 * @since 2021-08-10
 */
@Api(tags = "统计管理")
@RestController
@RequestMapping("/statistics/daily")
@CrossOrigin
public class DailyController {

    @Autowired
    private DailyService dailyService;

    @ApiOperation("统计一天的网站数据")
    @PostMapping("/save/{day}")
    public Result staData(@PathVariable("day") String day){
        boolean flag = dailyService.staData(day);
        if(!flag){
            return Result.error().message("日期格式错误,请选择正确日期(yyyy-MM-dd)");
        }
        return Result.ok();
    }

    @ApiOperation("生成统计折线图")
    @GetMapping("/lineChart")
    public Result showLineChart(ChartConditionVo chartConditionVo){
        Map<String,Object> map = dailyService.showLineChart(chartConditionVo);
        if(map == null){
            return Result.error().message("未查询到所需数据,请确认查询条件");
        }
        return Result.ok().data(map);
    }

}

