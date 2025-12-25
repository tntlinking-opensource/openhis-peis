package com.center.medical.outside.bean.Vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * token信息，该信息用户返回给客户端，客户端请求携带accessToken进行授权登录校验
 *
 * @author 路飞船长
 * @since 2024-11-06 15:14:06
 */
@Data
public class AuthTokenVO {

    @ApiModelProperty("授权令牌")
    private String accessToken;

    @ApiModelProperty("有效期，单位：秒")
    private Long expiresIn;
}
