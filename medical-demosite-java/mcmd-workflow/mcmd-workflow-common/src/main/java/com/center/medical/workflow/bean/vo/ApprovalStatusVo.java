package com.center.medical.workflow.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 我的审批情况 返回数据
 */
@Data
public class ApprovalStatusVo implements Serializable {
    private static final long serialVersionUID = 2129839492179978374L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "实例名称")
    private String caseName;

    @ApiModelProperty(value = "状态：-1.失效 0.等待开始 1.进行中 2.已通过 3.被驳回")
    private Integer status;

    @ApiModelProperty(value = "进度")
    private String schedule;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "当前审批人")
    private String userName;


    @ApiModelProperty(value = "类型标识")
    private String typeFlag;
}
