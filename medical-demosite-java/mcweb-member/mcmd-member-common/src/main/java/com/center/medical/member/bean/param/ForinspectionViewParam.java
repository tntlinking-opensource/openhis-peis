package com.center.medical.member.bean.param;

import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
//迟补检回访分页查询参数
public class ForinspectionViewParam extends BaseParam implements Serializable {

    private static final long serialVersionUID = -1598295336220627132L;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = " 登记开始时间")
    private Date regStartTime;

    @ApiModelProperty(value = " 登记结束时间")
    private Date regEndTime;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "公司")
    private String orgName;

    @ApiModelProperty(value = " 预约开始时间")
    private Date startDate;

    @ApiModelProperty(value = " 预约结束时间")
    private Date endDate;

    @ApiModelProperty(value = "处理结果")
    private List<String> sflj;

    @ApiModelProperty(value = "类型")
    private String type;

    @ApiModelProperty(value = " 处理开始时间")
    private Date visitStartTime;

    @ApiModelProperty(value = " 处理结束时间")
    private Date visitEndTime;

    @ApiModelProperty(value = " 预处理开始时间")
    private Date preStartTime;

    @ApiModelProperty(value = " 预处理结束时间")
    private Date preEndTime;

    @ApiModelProperty(value = "项目名称")
    private String itemName;

    @ApiModelProperty(value = "体检分类")
    private String useCodeHiden;

    @ApiParam(hidden = true)
    @ApiModelProperty(value = "标识，1或2或3")
    private Integer flag;

    @ApiParam(hidden = true)
    @ApiModelProperty(value = "拼接的参数")
    private String join;

    @ApiModelProperty(value = "分中心")
    private String fzx;

    @ApiModelProperty(value = "销售经理id")
    private String xsjlid;
}


