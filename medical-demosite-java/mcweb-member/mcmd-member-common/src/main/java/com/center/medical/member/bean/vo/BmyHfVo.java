package com.center.medical.member.bean.vo;

import com.center.medical.bean.model.Peispatient;
import com.center.medical.member.bean.model.Satisfaction;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 满意度回访-不满意回访数据
 */
@Data
public class BmyHfVo implements Serializable {
    private static final long serialVersionUID = -7041368880711958891L;

    @ApiModelProperty(value = "体检者表")
    private Peispatient peispatient;

    @ApiModelProperty(value = "客户满意度")
    private Satisfaction satisfaction;
}
