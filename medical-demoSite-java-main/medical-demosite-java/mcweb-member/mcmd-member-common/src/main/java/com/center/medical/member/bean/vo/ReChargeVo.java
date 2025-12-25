package com.center.medical.member.bean.vo;

import com.center.medical.bean.model.Peispatientarchive;
import com.center.medical.finance.bean.model.Card;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class ReChargeVo implements Serializable {
    private static final long serialVersionUID = 6999281370242513772L;


    @ApiModelProperty(value = "体检者档案表")
    private Peispatientarchive peispatientarchive;

    @ApiModelProperty(value = "体检卡")
    private Card Card;

    @ApiModelProperty(value = "分中心")
    private String cid;

    @ApiModelProperty(value = "姓名")
    private String userName;
}
