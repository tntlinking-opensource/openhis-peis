package com.center.medical.abteilung.bean.vo;

import com.center.medical.sellcrm.bean.model.Riskclient;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 危机值提报详情数据
 */
@Data
public class RiskclientInfoVo implements Serializable {

    private static final long serialVersionUID = 8966229048326362959L;

    @ApiModelProperty(value = "高危人员管理表")
    private Riskclient riskclient;

    @ApiModelProperty(value = "开单医生")
    private String doctorapply;

    @ApiModelProperty(value = "团体名称")
    private String orgName;

    @ApiModelProperty(value = "团体部门")
    private String orgDepart;

    @ApiModelProperty(value = "危急值级别")
    private Integer wjzjb;

    @ApiModelProperty(value = "危机值小结")
    private String wjzxj;

    @ApiModelProperty(value = "是否生成临时报告，1是，0或空否")
    private Integer interimReport;

    @ApiModelProperty(value = "提报科室")
    private String deptName;
}
