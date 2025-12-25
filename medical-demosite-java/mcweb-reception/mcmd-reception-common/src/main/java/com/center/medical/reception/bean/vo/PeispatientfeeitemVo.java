package com.center.medical.reception.bean.vo;

import com.center.medical.bean.model.Peispatientfeeitem;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: 路飞
 * @date: 2023-03-16 16:11
 * @description: 体检者收费项目信息
 */
@Data
public class PeispatientfeeitemVo extends Peispatientfeeitem implements Serializable {
    private static final long serialVersionUID = -7498305083078269516L;

    @ApiModelProperty(value = "加项医师")
    private String name;

    @ApiModelProperty(value = "科室名称")
    private String ksmc;

    @ApiModelProperty(value = "分组类型：0.组内选 1.组间选 2.组任选")
    private Integer groupType;


}
