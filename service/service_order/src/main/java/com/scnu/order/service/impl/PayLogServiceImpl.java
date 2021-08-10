package com.scnu.order.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.wxpay.sdk.WXPayUtil;
import com.scnu.exceptions.OrderException;
import com.scnu.order.client.EduClient;
import com.scnu.order.entity.Order;
import com.scnu.order.entity.PayLog;
import com.scnu.order.mapper.OrderMapper;
import com.scnu.order.mapper.PayLogMapper;
import com.scnu.order.service.PayLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scnu.order.utils.HttpClient;
import com.scnu.utils.Code;
import com.scnu.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 支付日志表 服务实现类
 * </p>
 *
 * @author xhy
 * @since 2021-08-09
 */
@Service
public class PayLogServiceImpl extends ServiceImpl<PayLogMapper, PayLog> implements PayLogService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private EduClient eduClient;

    @Override
    public Map<String, Object> createQRcode(String orderNo) {
        //1.根据订单号查询订单信息
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",orderNo);
        Order order = orderMapper.selectOne(wrapper);
        //2.使用map设置生成二维码需要的参数
        Map m = new HashMap();
        //设置支付参数
        m.put("appid", "wx74862e0dfcf69954");
        m.put("mch_id", "1558950191");
        m.put("nonce_str", WXPayUtil.generateNonceStr());
        m.put("body", order.getCourseTitle());
        m.put("out_trade_no", orderNo);
        m.put("total_fee", order.getTotalFee().multiply(new BigDecimal("100")).longValue()+"");
        m.put("spbill_create_ip", "127.0.0.1");
        m.put("notify_url", "http://guli.shop/api/order/weixinPay/weixinNotify\n");
        m.put("trade_type", "NATIVE");
        //3.发送httpclient请求,传递参数xml格式,微信支付提供的固定的地址
        //2、HTTPClient来根据URL访问第三方接口并且传递参数
        HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");

        //client设置参数
        try {
            client.setXmlParam(WXPayUtil.generateSignedXml(m, "T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
            client.setHttps(true);
            client.post();
            //3、返回第三方的数据,返回的是一个xml格式
            String xml = client.getContent();
            Map<String,String> resultMap = WXPayUtil.xmlToMap(xml);
            //最终返回数据的封装
            Map<String,Object> map = new HashMap<>();
            map.put("out_trade_no", orderNo);
            map.put("course_id", order.getCourseId());
            map.put("total_fee", order.getTotalFee());
            map.put("result_code", resultMap.get("result_code"));   //返回二维码操作的状态码
            map.put("code_url", resultMap.get("code_url"));         //二维码的地址
            //4.得到发送请求返回的结果
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            throw new OrderException("生成二维码失败");
        }
    }

    @Override
    public Map<String, String> queryStatus(String orderNo) {
        //1.封装参数
        Map m = new HashMap<>();
        m.put("appid", "wx74862e0dfcf69954");
        m.put("mch_id", "1558950191");
        m.put("out_trade_no", orderNo);
        m.put("nonce_str", WXPayUtil.generateNonceStr());
        //2.发送httpclient请求,请求微信固定地址
        HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/orderquery");
        try {
            client.setXmlParam(WXPayUtil.generateSignedXml(m,"T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
            client.setHttps(true);
            client.post();
            //3.返回请求的数据
            String content = client.getContent();
            Map<String,String> resultMap = WXPayUtil.xmlToMap(content);
            return resultMap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Transactional
    @Override
    public void updateOrderStatus(Map<String, String> statusMap) {
        //1.更新订单状态
        String orderNo = statusMap.get("out_trade_no");
        QueryWrapper<Order> wrapperOrder = new QueryWrapper<>();
        wrapperOrder.eq("order_no",orderNo);
        Order order = orderMapper.selectOne(wrapperOrder);
        if(order.getStatus() == 1){  //订单已经更新过了
            return;
        }
        order.setStatus(1);
        int result = orderMapper.updateById(order);
        if(result != 1){
            throw new OrderException("订单更新失败");
        }
        //2.添加支付记录
        PayLog payLog = new PayLog();
        payLog.setPayTime(new Date());
        payLog.setOrderNo(orderNo);
        payLog.setPayType(1);   //微信支付
        payLog.setAttr(JSONObject.toJSONString(statusMap));
        payLog.setTotalFee(order.getTotalFee());
        payLog.setTradeState(statusMap.get("trade_state")); //支付状态
        payLog.setTransactionId(statusMap.get("transaction_id"));   //交易流水号
        int result1 = baseMapper.insert(payLog);
        if(result1 != 1){
            throw new OrderException("添加支付记录失败");
        }
        //3.TODO
        //课程购买数增加的问题,如何解决前台大量购买,保证课程表课程购买数正确的问题?
        //这里用了synchronized
        Result result2 = eduClient.orderBuy(order.getCourseId());
        if(result2.getCode() == Code.ERROR){
            throw new OrderException("课程表的课程购买数出错");
        }
    }
}
