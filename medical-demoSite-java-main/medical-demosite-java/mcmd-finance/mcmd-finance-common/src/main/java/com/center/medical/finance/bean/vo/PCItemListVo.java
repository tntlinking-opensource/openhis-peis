package com.center.medical.finance.bean.vo;

import com.center.medical.bean.enums.MedicalType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 右中-项目列表数据
 */
@Data
public class PCItemListVo implements Serializable {
    private static final long serialVersionUID = -9131974107171205895L;

    @ApiModelProperty(value = "体检套餐ID")
    private String idTjtc;

    @ApiModelProperty(value = "订单id")
    private String ddId;

    @ApiModelProperty(value = "分组id")
    private String groupId;

    @ApiModelProperty(value = "体检类型：0.健康体检 1.职业体检 2.综合 3.复查")
    private String idExamtype;

    @ApiModelProperty(value = "接害因素")
    private String jhys;

    /**
     * @see MedicalType
     */
    @ApiModelProperty(value = "体检类别：0.上岗前 1.在岗期间 2.离岗时 3.离岗后 4.应急")
    private String medicaltype;
}
