package com.scnu.order.service;

import com.scnu.order.entity.PayLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author xhy
 * @since 2021-08-09
 */
public interface PayLogService extends IService<PayLog> {

    //生成支付二维码
    Map<String, Object> createQRcode(String orderNo);

    //根据订单号查询订单支付状态
    Map<String, String> queryStatus(String orderNo);

    //更新数据库表的数据,订单表,订单支付表,课程购买数
    void updateOrderStatus(Map<String, String> statusMap);
}
