package com.center.medical.member.bean.param;

import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: 路飞
 * @date: 2022-11-24 17:47
 * @description: 会员管理-生日关怀列表数据查询参数
 */
@Data
public class MemberbirthdayParam extends BaseParam implements Serializable {
    private static final long serialVersionUID = 2480831930509126991L;
    
    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "身份证号")
    private String idcardno;

    @ApiModelProperty(value = "性别：0.男 1.女 2.通用")
    private Integer idSex;

    @ApiModelProperty(value = "生日，格式：yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    @ApiModelProperty(value = "年龄")
    private Integer age;

    @ApiParam(hidden = true)
    @ApiModelProperty(value = "最小自然年")
    private Integer minYear;

    @ApiParam(hidden = true)
    @ApiModelProperty(value = "最大自然年")
    private Integer maxYear;

}
