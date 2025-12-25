package com.center.medical.reception.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 返回设置数据
 */
@Data
public class ReturnListVo implements Serializable {
    private static final long serialVersionUID = 2282326219543191513L;

    @ApiModelProperty(value = "关闭重发")
    private Integer closeChongfa;

    @ApiModelProperty(value = "中间库地址")
    private String lisPacsUrl;

    @ApiModelProperty(value = "登录用户用户名")
    private String userName;

    @ApiModelProperty(value = "登录用户分中心")
    private String cid;

    @ApiModelProperty(value = "默认导引单样式")
    private String dydStyle;

    public ReturnListVo(Integer closeChongfa, String lisPacsUrl, String userName, String cid, String dydStyle) {
        this.closeChongfa = closeChongfa;
        this.lisPacsUrl = lisPacsUrl;
        this.userName = userName;
        this.cid = cid;
        this.dydStyle = dydStyle;
    }

    public ReturnListVo() {
    }
}
