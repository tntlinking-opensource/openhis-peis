package com.center.medical.system.bean.dto;

import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: 路飞
 * @date: 2023-04-28 11:03
 * @description: 系统用户简单信息
 */
@Data
public class SysUserSimpleDto implements Serializable {
    private static final long serialVersionUID = -4448711402725201357L;

    /**
     * 用户编号（继承于原体检系统的用户id：userId）
     */
    @Excel(name = "用户编号", cellType = Excel.ColumnType.STRING, prompt = "用户编号")
    private String userNo;

    /**
     * 用户账号
     */
    @Excel(name = "登录名称")
    private String userName;

    /**
     * 用户昵称
     */
    @Excel(name = "用户名称")
    private String nickName;

    @ApiModelProperty(value = "输入码")
    private String inputCode;

    @ApiModelProperty(value = "分中心名称")
    private String fzxmc;
}
