package com.scnu.statistics.service;

import com.scnu.statistics.entity.Daily;
import com.baomidou.mybatisplus.extension.service.IService;
import com.scnu.statistics.vo.ChartConditionVo;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author xhy
 * @since 2021-08-10
 */
public interface DailyService extends IService<Daily> {

    //统计一天的数据
    boolean staData(String day);

    Map<String, Object> showLineChart(ChartConditionVo chartConditionVo);
}
