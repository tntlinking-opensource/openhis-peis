package com.center.medical.reservation.bean.param;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 老系统预约参数
 */
@Data
public class OldSystemReParam implements Serializable {
    private static final long serialVersionUID = -3256698285867139651L;

    @ApiModelProperty(value = "预约id")
    private String id;

    @ApiModelProperty(value = "分中心ID")
    private String branchId;

    @ApiModelProperty(value = "性别：0.男 1.女 2.通用", required = true, position = 4)
    private Integer sex;

    @ApiModelProperty(value = "身份证号", required = true)
    private String idcard;

    @ApiModelProperty(value = "预约类型ID", required = true)
    private Integer levelId;

    @ApiModelProperty(value = "预约类型名称")
    private String levelName;

    @ApiModelProperty(value = "预约手机号", required = true)
    private String mobile;

    @ApiModelProperty(value = "姓名", required = true)
    private String realName;

    @ApiModelProperty(value = "预约备注")
    private String remark;

    @ApiModelProperty(value = "预约时间", required = true)
    private Date reservationDate;

    @ApiModelProperty(value = "预约时间段ID", required = true)
    private String timeId;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "第三方系统ID：本地传0", required = true)
    private String systemId;

    @ApiModelProperty(value = "预约状态：-1预约失败 1.待预约 2.已预约 3.预约结束")
    private Integer status;

    @ApiModelProperty(value = "预约号")
    private String reservationNo;

    @ApiModelProperty(value = "第三方订单ID")
    private String bizOrderNum;

    @ApiModelProperty(value = "团体ID(团检预约需要)")
    private String idOrg;

    @JsonProperty("fUsecodehiden")
    @ApiModelProperty(value = "订单类型：0.个检 1.团检")
    private Integer fUsecodehiden;

    @ApiModelProperty(value = "创建者id")
    private String creator;

    @ApiModelProperty(value = "更新者id")
    private String modifier;
}
