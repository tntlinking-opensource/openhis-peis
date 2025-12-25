package com.center.medical.datamove.oracle.bean.model;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

import lombok.Data;

import java.util.Date;

/**
 * 体检者档案表(Peispatientarchive)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:23:24
 */
@Data
@TableName("PEISPATIENTARCHIVE")
@ApiModel(value = "Peispatientarchive", description = "体检者档案表实体类")
public class Peispatientarchive extends Model<Peispatientarchive> implements Serializable {
    private static final long serialVersionUID = -78011885967844858L;

    @ApiModelProperty(value = "ID")
    private String idPatientarchive;

    @ApiModelProperty(value = "档案号")
    private String patientarchiveno;

    @ApiModelProperty(value = "一卡通号")
    private String patientcardno;

    @ApiModelProperty(value = "身份证号")
    private String idcardno;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "输入码")
    private String inputCode;

    @ApiModelProperty(value = "性别ID")
    private Integer idSex;

    @ApiModelProperty(value = "性别")
    private String sex;

    @ApiModelProperty(value = "年龄")
    private Double age;

    @ApiModelProperty(value = "年龄的时间基准")
    private Date agebasedate;

    @ApiModelProperty(value = "生日")
    private Date birthdate;

    @ApiModelProperty(value = "婚姻ID")
    private String idMarriage;

    @ApiModelProperty(value = "婚姻")
    private String marriage;

    @ApiModelProperty(value = "民族ID")
    private String idNation;

    @ApiModelProperty(value = "民族")
    private String nation;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "教育ID")
    private String idEducation;

    @ApiModelProperty(value = "教育程度")
    private String education;

    @ApiModelProperty(value = "职业ID")
    private String idOccupation;

    @ApiModelProperty(value = "职业")
    private String occupation;

    @ApiModelProperty(value = "地区ID")
    private String idResarea;

    @ApiModelProperty(value = "地区")
    private String resarea;

    @ApiModelProperty(value = "登记时间")
    private Date dateregister;

    @ApiModelProperty(value = "备注")
    private String note;

    @ApiModelProperty(value = "创建时间")
    private Date createDate;

    @ApiModelProperty(value = "更新时间")
    private Date modifyDate;

    @ApiModelProperty(value = "是否黑名单")
    private Integer ishmd;

    @ApiModelProperty(value = "黑名单备注")
    private String hmdbz;

    @ApiModelProperty(value = "回访状态")
    private Integer restatus;

    @ApiModelProperty(value = "0   ; 1")
    private Integer yjgzbj;

    @ApiModelProperty(value = "vip ; vvip等等")
    private String memberlevel;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "修改日期")
    private Date modifydate;

    @ApiModelProperty(value = "0:小学 1:初中 2:技校 3:职高 4:高中 5:中专 6:大专 7:大学 8:研究生以上")
    private Integer cultural;

    @ApiModelProperty(value = "0：个人 1：团检")
    private Integer isOrg;

    @ApiModelProperty(value = "创建人")
    private String membercreate;

    @ApiModelProperty(value = "客户经理")
    private String khjl;

    @ApiModelProperty(value = "积分")
    private Integer jf;

    @ApiModelProperty(value = "分中心")
    private String fzx;

    @ApiModelProperty(value = "单位")
    private String dw;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "${column.comment}")
    private String countreportoccupationxml;

    @ApiModelProperty(value = "${column.comment}")
    private Integer isDelete;

    @ApiModelProperty(value = "${column.comment}")
    private Integer isMain;

    @ApiModelProperty(value = "${column.comment}")
    private Integer source;

    @ApiModelProperty(value = "${column.comment}")
    private String oldCard;
}
