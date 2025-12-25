package com.center.medical.reception.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 获取最近人员列表 返回数据
 */
@Data
public class RecentPersonnelListVo implements Serializable {
    private static final long serialVersionUID = -4764054784174156706L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "性别 0男1女")
    private String idSex;

    @ApiModelProperty(value = "年龄")
    private Integer age;

    @ApiModelProperty(value = "套餐名称")
    private String tcmc;

    @ApiModelProperty(value = "团体名称")
    private String orgName;

    @ApiModelProperty(value = "分组名称")
    private String orgreservationgroupname;

}
