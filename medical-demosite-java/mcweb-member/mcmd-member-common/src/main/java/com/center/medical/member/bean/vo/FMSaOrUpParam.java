package com.center.medical.member.bean.vo;

import com.center.medical.member.bean.dto.ChargeGDDto;
import com.center.medical.member.bean.dto.FormdataDto;
import com.center.medical.member.bean.dto.MemberGDDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 新增家庭卡办理-保存参数
 */
@Data
public class FMSaOrUpParam implements Serializable {
    private static final long serialVersionUID = -6995082791397344238L;

    @ApiModelProperty(value = "收费信息参数")
    private List<ChargeGDDto> chargeGriddata;

    @ApiModelProperty(value = "其他家庭成员信息参数")
    private List<MemberGDDto> memberGriddata;

    @ApiModelProperty(value = "家庭卡信息")
    private FormdataDto formdata;

}
