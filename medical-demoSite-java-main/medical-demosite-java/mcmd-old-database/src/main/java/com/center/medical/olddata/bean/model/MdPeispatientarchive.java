package com.center.medical.olddata.bean.model;


import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 体检者档案表(MdPeispatientarchive)表实体类
 *
 * @author ay
 * @since 2023-09-04 09:16:14
 */
@Data
@TableName("md_peispatientarchive")
@ApiModel(value = "MdPeispatientarchive", description = "体检者档案表实体类")
public class MdPeispatientarchive extends Model<MdPeispatientarchive> implements Serializable {
    private static final long serialVersionUID = 830860721185062030L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;


    @ApiModelProperty(value = "档案ID")
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
    private Integer age;


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


    @ApiModelProperty(value = "是否黑名单")
    private Integer ishmd;


    @ApiModelProperty(value = "黑名单备注")
    private String hmdbz;


    @ApiModelProperty(value = "回访状态")
    private Integer restatus;


    @ApiModelProperty(value = "0   ; 1")
    private Integer yjgzbj;


    @ApiModelProperty(value = "vip、vvip等等")
    private String memberlevel;


    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createdate;


    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifydate;


    @ApiModelProperty(value = "文化程度，详见：com.world.center.bean.enums.CulturalLevel")
    private Integer cultural;


    @ApiModelProperty(value = "是否团检 0：个人 1：团检")
    private Integer isOrg;


    @ApiModelProperty(value = "创建人")
    private String membercreate;


    @ApiModelProperty(value = "客户经理")
    private String khjl;


    @ApiModelProperty(value = "积分")
    private String jf;


    @ApiModelProperty(value = "分中心")
    private String fzx;


    @ApiModelProperty(value = "单位")
    private String dw;


    @ApiModelProperty(value = "是否删除：0.未删除 1.已删除")
    private Integer isDelete;


    @ApiModelProperty(value = "客户证件类型，详见：com.world.center.bean.enums.CusCardType")
    private Integer countreportoccupationxml;


    @ApiModelProperty(value = "是否主持卡人  1是  0或null不是")
    private Integer isMain;


    @ApiModelProperty(value = "档案来源  1家庭卡、超级会员管理新增的档案")
    private Integer source;


    @ApiModelProperty(value = "绑定前用过的旧卡号")
    private String oldCard;

}
