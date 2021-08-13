package com.scnu.statistics.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.scnu.exceptions.DailyException;
import com.scnu.statistics.client.UserClient;
import com.scnu.statistics.entity.Daily;
import com.scnu.statistics.mapper.DailyMapper;
import com.scnu.statistics.service.DailyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scnu.statistics.vo.ChartConditionVo;
import com.scnu.utils.RandomUtil;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author xhy
 * @since 2021-08-10
 */
@Service
public class DailyServiceImpl extends ServiceImpl<DailyMapper, Daily> implements DailyService {

    @Autowired
    private UserClient userClient;

    @Transactional
    @Override
    public boolean staData(String day) {
        if(!checkDay(day)){
            return false;
        }
        Integer registerNum = userClient.registerNum(day);
        if(registerNum == null){
            throw new DailyException("统计注册人数失败");
        }
        Integer loginNum = RandomUtils.nextInt(100, 200);//TODO
        Integer videoViewNum = RandomUtils.nextInt(100, 200);//TODO
        Integer courseNum = RandomUtils.nextInt(100, 200);//TODO
        QueryWrapper<Daily> wrapper = new QueryWrapper<>();
        wrapper.eq("date_calculated",day);
        Daily daily = baseMapper.selectOne(wrapper);
        boolean flag = false;
        if(daily == null){
            flag = true;
            daily = new Daily();
            daily.setDateCalculated(day);
        }
        daily.setCourseNum(courseNum);
        daily.setLoginNum(loginNum);
        daily.setRegisterNum(registerNum);
        daily.setVideoViewNum(videoViewNum);
        if(!flag){
            int result = baseMapper.updateById(daily);
            if(result != 1){
                throw new DailyException("更新" + day + "日期的数据异常");
            }
        }
        else{
            int result = baseMapper.insert(daily);
            if(result != 1){
                throw new DailyException("添加" + day + "日期的数据异常");
            }
        }
        return true;
    }

    private boolean checkDay(String day) {
        if(StringUtils.isEmpty(day)){
            return false;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // 设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
        sdf.setLenient(false);
        try {
            sdf.parse(day);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    @Override
    public Map<String, Object> showLineChart(ChartConditionVo chartConditionVo) {
        String type = chartConditionVo.getType();
        String begin = chartConditionVo.getBegin();
        String end = chartConditionVo.getEnd();
        QueryWrapper<Daily> wrapper = new QueryWrapper<>();
        wrapper.between("date_calculated",begin,end);
        wrapper.select("date_calculated",type);
        wrapper.orderByAsc("date_calculated");
        List<Daily> dailies = baseMapper.selectList(wrapper);
        if(dailies.size() == 0){
            return null;
        }
        int len = dailies.size();
        List<String> dayList = new ArrayList<>(len);
        List<Integer> numList = new ArrayList<>(len);
        for (Daily daily : dailies) {
            dayList.add(daily.getDateCalculated());
            switch (type){
                case "login_num":
                    numList.add(daily.getLoginNum());
                    break;
                case "register_num":
                    numList.add(daily.getRegisterNum());
                    break;
                case "video_view_num":
                    numList.add(daily.getVideoViewNum());
                    break;
                case "course_num":
                    numList.add(daily.getCourseNum());
                    break;
                default:
                    numList.add(0);
                    break;
            }
        }
        Map<String,Object> map = new HashMap<>();
        map.put("dayList",dayList);
        map.put("numList",numList);
        return map;
    }
}
