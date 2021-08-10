package com.scnu.utils.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value="OrderUser对象", description="远程调用,订单页面用户相关信息")
@Data
public class OrderUser {

    @ApiModelProperty(value = "会员id")
    private String memberId;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "昵称")
    private String nickname;
}
