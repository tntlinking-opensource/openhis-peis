package com.center.medical.system.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 修改项目状态-查询 返回数据
 */
@Data
public class FindProjectStatusVo implements Serializable {

    private static final long serialVersionUID = 1933917581197569448L;

    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "体检号")
    private String idPatient;

    @ApiModelProperty(value = "0未检1已检")
    private Integer fExaminated;

    @ApiModelProperty(value = "收费项目名称:必填/如果基础里面名称没填，此字段填“未命名”，查id未找到此字段填为“未知项”")
    private String examfeeitemName;

    @ApiModelProperty(value = "科室ID")
    private String idKs;

    @ApiModelProperty(value = "科室名称")
    private String ksName;
}
