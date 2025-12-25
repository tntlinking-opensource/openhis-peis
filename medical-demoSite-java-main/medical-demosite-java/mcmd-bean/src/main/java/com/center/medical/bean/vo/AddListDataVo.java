package com.center.medical.bean.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 科室加项右侧返回数据
 */
@Data
public class AddListDataVo implements Serializable {
    private static final long serialVersionUID = 5017961623241345761L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "收费项目ID")
    private String itemId;

    @ApiModelProperty(value = "收费检查项目名称")
    private String examfeeitemName;

    @ApiModelProperty(value = "医生名称")
    private String doctorUsername;

    @ApiModelProperty(value = "价格")
    private String price;

    @ApiModelProperty(value = "体检者收费项目备注")
    private String remarks;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "PACS体检者收费项目ID")
    private String feeitemId;

    @ApiModelProperty(value = "序号")
    private Integer qty;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "所属科室")
    private String ssks;

    @JsonProperty("fFeecharged")
    @ApiModelProperty(value = "已收费")
    private String fFeecharged;


}
