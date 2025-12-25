package com.center.medical.outreach.bean.param;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 合作单位登录参数
 */
@Data
public class CooCustomerLoginParam implements Serializable {
    private static final long serialVersionUID = -1537198201431083130L;

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    protected String userName;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    protected String password;

}
