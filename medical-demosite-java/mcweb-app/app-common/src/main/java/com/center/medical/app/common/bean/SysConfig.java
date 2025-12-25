/*
 * Copyright (c) 2023-2999 青岛沃德医疗 All rights reserved.
 *
 * http://www.world.com/
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */

package com.center.medical.app.common.bean;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 系统配置信息
 *
 * @author yami
 */
@Data
@TableName("md_sys_config")
public class SysConfig {
    @TableId
    private Long id;

    @NotBlank(message = "参数名不能为空")
    private String paramKey;

    @NotBlank(message = "参数值不能为空")
    private String paramValue;

    @NotBlank(message = "备注")
    private String remark;

}
