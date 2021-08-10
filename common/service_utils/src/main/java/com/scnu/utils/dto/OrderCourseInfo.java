package com.scnu.utils.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@ApiModel(value="OrderCourseInfo对象", description="远程调用,订单页面的课程相关信息")
@Data
public class OrderCourseInfo {

    @ApiModelProperty(value = "课程id")
    private String courseId;

    @ApiModelProperty(value = "课程标题")
    private String courseTitle;

    @ApiModelProperty(value = "课程封面图片路径")
    private String courseCover;

    @ApiModelProperty(value = "讲师姓名")
    private String teacherName;

    @ApiModelProperty(value = "课程销售价格，设置为0则可免费观看")
    private BigDecimal totalFee;


}
