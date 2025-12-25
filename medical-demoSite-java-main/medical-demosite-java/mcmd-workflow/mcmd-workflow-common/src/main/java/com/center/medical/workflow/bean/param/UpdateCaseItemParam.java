package com.center.medical.workflow.bean.param;


import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 实例节点更新信息
 *
 * @author 路飞
 * @since 2023-11-08 16:58:11
 */
@Data
@ApiModel(value = "UpdateCaseItemParam", description = "实例节点更新信息")
public class UpdateCaseItemParam extends Model<UpdateCaseItemParam> implements Serializable {
    private static final long serialVersionUID = 111426062894360472L;

    @ApiModelProperty(value = "实例节点ID")
    private String itemId;

    @ApiModelProperty(value = "实例ID")
    private String caseId;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "状态：0.等待开始(上一级还未通过) 1.进行中(上一级已通过) 2.已通过 3.被驳回")
    private Integer status;
}
