package com.center.medical.system.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 通知配置表 添加或更新参数
 */
@Data
public class NoticeConfigPageParam implements Serializable {
    private static final long serialVersionUID = -7548110708898680969L;


    @ApiModelProperty(value = "通知名称")
    private String noticeName;




}
