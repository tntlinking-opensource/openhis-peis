package com.center.medical.reception.bean.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 获取档案记录返回数据
 */
@Data
public class RecordListVo implements Serializable {
    private static final long serialVersionUID = 8861120141888042235L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "档案号")
    private String patientarchiveno;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "性别：0.男 1.女 2.通用")
    private Integer idSex;

    @ApiModelProperty(value = "生日")
    private Date birthdate;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "身份证号")
    private String idcardno;

    @ApiModelProperty(value = "民族ID")
    private String idNation;

    @ApiModelProperty(value = "体检地址")
    private String address;

    @ApiModelProperty(value = "文化水平，详见：com.center.medical.bean.enums.CulturalLevel")
    private Integer cultural;

    @ApiModelProperty(value = "黑名单备注")
    private String isHmdb;

    @ApiModelProperty(value = "黑名单备注")
    private String hmdbz;

    @ApiModelProperty(value = "vip、vvip等等")
    private String memberlevel;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "个数")
    private String examcounts;

    @ApiModelProperty(value = "婚姻id")
    private String idMarriage;

    @ApiModelProperty(value = "婚姻")
    private String marriage;

    @ApiModelProperty(value = "客户证件类型：1.身份证 2.护照 6.军人证  7.港澳通行证/回乡证或台胞证")
    private Integer countreportoccupationxml;

    @ApiModelProperty(value = "一卡通号")
    private String patientcardno;

    @ApiModelProperty(value = "金额")
    private String money;
}
