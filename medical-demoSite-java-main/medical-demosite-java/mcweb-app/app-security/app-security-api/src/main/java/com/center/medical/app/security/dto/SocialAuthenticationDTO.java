package com.center.medical.app.security.dto;

import com.center.medical.app.security.common.dto.AuthenticationDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用于登陆传递账号密码
 *
 * @author FrozenWatermelon
 * @date 2020/7/1
 */
@Data
public class SocialAuthenticationDTO extends AuthenticationDTO {


    @ApiModelProperty(value = "零时的uid，微信公众号支付需要openid，但用户又不绑定社交账号，所以这个openId是临时的")
    private String tempUid;
}
