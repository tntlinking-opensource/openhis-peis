package com.center.medical.pacslis.bean.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * @author xhp
 * @since 2023-02-28 11:20
 */
@ApiModel("插入中间库接口固定参数对象-收费项目信息")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MiddleDbItemDto {
    @ApiModelProperty(value = "md_peispatientfeeitem.id")
    private String feeitemId;
    @ApiModelProperty(value = "科室名称")
    private String departName;
    @ApiModelProperty(value = "科室接口类型")
    private String transfterTarget;
    @ApiModelProperty(value = "sysDept.id")
    private Integer idDepart;
    @ApiModelProperty(value = "SEND")
    private String hisExecDepartCode;
    @ApiModelProperty(value = "收费项目intid")
    private Integer idExamFeeItem;
    @ApiModelProperty(value = "收费项目名称")
    private String examFeeItemName;
    @ApiModelProperty(value = "收费项目原价")
    private Double unitPrice;
    @ApiModelProperty(value = "收费项目实际价格")
    private Double factPrice;
    @ApiModelProperty(value = "收费项目标本类型id")
    private Integer idLabType;
    @ApiModelProperty(value = "收费项目标本类型名称")
    private String labTypeName;
    @ApiModelProperty(value = "收费项目接口代码")
    private String examFeeItemCode;
    //销售打印费类=4 为常规科室，需要插入IntPatientTransFeeItem
    @ApiModelProperty(value = "收费项目销售打印分类")
    private String xsdyfl;
    @ApiModelProperty(value = "收款时间")
    private Timestamp feeChargeTime;
    @ApiModelProperty(value = "标本种类名称")
    private String labTypeCode;
    @TableField("外送机构名称")
    private String feeTypeName;
    @TableField("外送机构id")
    private String feeTypeCode;
}
