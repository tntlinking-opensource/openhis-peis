package com.center.medical.report.bean.vo;

import com.center.medical.report.bean.dto.PersonAptitudeDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 报告设置返回数据
 */
@Data
public class ReportConfigVo implements Serializable {
    private static final long serialVersionUID = -3112643797163720290L;

    @ApiModelProperty(value = "小程序码")
    private String miniProgramCode;

    @ApiModelProperty(value = "二维码")
    private String QRCode;

    @ApiModelProperty(value = "标志或图标")
    private String logo;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "电子邮箱")
    private String email;

    @ApiModelProperty(value = "邮编")
    private String postalCode;

    @ApiModelProperty(value = "传真")
    private String fax;

    @ApiModelProperty(value = "资质证书号")
    private String certificateNo;

    @ApiModelProperty(value = "检字")
    private String inspect;

    @ApiModelProperty(value = "图片")
    private String pic;

    @ApiModelProperty(value = "盖章图片")
    private String stampPic;

    @ApiModelProperty(value = "监制")
    private String producer;

    @ApiModelProperty(value = "监制")
    private List<PersonAptitudeDto> personAptitude;

    @ApiModelProperty(value = "职业盖章图片")
    private String professionalSeal;

    @ApiModelProperty(value = "生成正式报告的隐私报告尾页 1是0否")
    private String generatePrivacyReport;

}
