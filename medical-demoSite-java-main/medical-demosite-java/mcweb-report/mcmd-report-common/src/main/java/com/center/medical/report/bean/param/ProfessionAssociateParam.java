package com.center.medical.report.bean.param;

import com.center.medical.bean.param.BaseParam;
import com.center.medical.common.utils.StringUtils;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.io.Serializable;

/**
 * 职业报告交接分页参数
 */
@Data
public class ProfessionAssociateParam extends BaseParam implements Serializable {
    private static final long serialVersionUID = -3258572225638246406L;

    @ApiModelProperty(value = "是否补全：true是 false否")
    private String autoFill;

    @ApiModelProperty(value = "类型：0.健康 1.职业(必填！！！)")
    private String diseaseHealth;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "已交接 true是 false否")
    private String isHandover;

    @ApiModelProperty(value = "未交接 true是 false否 ")
    private String isNotHandover;

    @ApiModelProperty(value = "单位名称或输入码")
    private String numorgresv;

    @ApiModelProperty(value = "状态")
    private String status;

    @ApiModelProperty(value = "姓名或输入码")
    private String name;

    @ApiModelProperty(value = "交接人")
    private String joinPersonId;

    @ApiModelProperty(value = "销售经理")
    private String xsjlid;

    @ApiParam(hidden = true)
    @ApiModelProperty(value = "标识，如果没参数，这个标识就为ture，否则为false")
    private String flag;


    @ApiModelProperty(value = "发送方式ID")
    private String grantId;


    public String getJoinPersonId() {
        if (StringUtils.isEmpty(joinPersonId)){
            return null;
        }
        return joinPersonId.trim().toUpperCase();
    }

    public String getName() {
        if (StringUtils.isEmpty(name)){
            return null;
        }
        return name.toUpperCase();
    }
}
