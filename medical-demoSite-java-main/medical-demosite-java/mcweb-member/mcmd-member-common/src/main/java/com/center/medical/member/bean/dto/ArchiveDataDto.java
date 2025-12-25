package com.center.medical.member.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 新增家庭卡办理-保存参数
 */
@Data
public class ArchiveDataDto implements Serializable {

    private static final long serialVersionUID = 3465669163859159641L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "身份证号")
    private String idcardno;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "性别：0.男 1.女 2.通用")
    private Integer idSex;

    @ApiModelProperty(value = "单位")
    private String dw;
    
    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "生日")
    private Date birthdate;

    @ApiModelProperty(value = "分中心")
    private String fzx;

    @ApiModelProperty(value = "vip、vvip等等")
    private String memberlevel;

    @ApiModelProperty(value = "客户经理")
    private String khjl;

    @ApiModelProperty(value = "备注")
    private String note;

    @ApiModelProperty(value = "客户经理名称")
    private String khjlName;

    @ApiModelProperty(value = "性别")
    private String sex;
}
