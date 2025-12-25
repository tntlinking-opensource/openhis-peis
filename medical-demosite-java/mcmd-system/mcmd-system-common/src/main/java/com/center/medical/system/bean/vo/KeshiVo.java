package com.center.medical.system.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: 路飞
 * @date: 2022-11-29 12:05
 * @description: 科室列表数据
 */
@Data
public class KeshiVo implements Serializable {
    private static final long serialVersionUID = 3900611803480428018L;

    @ApiModelProperty(value = "科室id")
    private Long deptId;

    @ApiModelProperty(value = "科室编码，对应原系统的deptId")
    private String deptNo;

    @ApiModelProperty(value = "科室名称")
    private String deptName;

    @ApiModelProperty(value = "拼音输入码")
    private String inputCode;

    @ApiModelProperty(value = "接口类型，详见：com.center.medical.bean.enums.LpsJklxType")
    private String jklx;

    @ApiModelProperty(value = "科室号")
    private String ksh;

    @ApiModelProperty(value = "图片地址")
    private String imgpath;
}
