package com.scnu.order.controller;

import com.scnu.order.service.PayLogService;
import com.scnu.utils.Code;
import com.scnu.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author xhy
 * @since 2021-08-09
 */
@Api(tags = "微信支付管理")
@RestController
@RequestMapping("/orderservice/paylog")
@CrossOrigin
public class PayLogController {

    @Autowired
    private PayLogService payLogService;

    @ApiOperation("根据订单号生成微信支付二维码")
    @GetMapping("/QRcode/{orderNo}")
    public Result createQRcode(@PathVariable("orderNo") String orderNo){
        //返回信息,包含二维码地址和其他相关信息
        Map<String,Object> map = payLogService.createQRcode(orderNo);
        System.out.println("****二维码map:" + map);
        return Result.ok().data(map);
    }

    @ApiOperation("查询订单的支付状态")
    @GetMapping("/queryStatus/{orderNo}")
    public Result queryStatus(@PathVariable("orderNo") String orderNo){
        Map<String,String> statusMap = payLogService.queryStatus(orderNo);
        System.out.println("****订单状态map:" + statusMap);
        if(statusMap == null){
            return Result.error().message("支付出错了...");
        }
        if(statusMap.get("trade_state").equals("SUCCESS")){ //支付成功
            //添加记录到支付表,更新订单表订单状态
            payLogService.updateOrderStatus(statusMap);
            return Result.ok().message("支付成功");
        }
        return Result.ok().message("支付中").code(Code.PENDING);
    }
}

