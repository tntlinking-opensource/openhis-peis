package com.center.medical.bean.vo;

import com.center.medical.bean.dto.GetTempFeeitemDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 科室临时加项表 返回数据
 */
@Data
public class GetTempFeeitemVo implements Serializable {
    private static final long serialVersionUID = -2734214004511726347L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "性别ID")
    private Integer idSex;

    @ApiModelProperty(value = "年龄")
    private Integer age;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "体检类型：0.健康体检 1.职业体检 2.综合 3.复查")
    private String idExamtype;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "合计价格")
    private Double TotalPrice;

    @ApiModelProperty(value = "收费项目数据")
    private List<GetTempFeeitemDto> list;
}
