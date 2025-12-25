package com.center.medical.bean.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * KS问诊——个人基本信息(WzGrxx)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:52:18
 */
@Data
@TableName("md_wz_grxx")
@ApiModel(value = "WzGrxx", description = "KS问诊——个人基本信息实体类")
public class WzGrxx extends Model<WzGrxx> implements Serializable {
    private static final long serialVersionUID = -92994865941123833L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "登记流水")
    private String djls;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "体检次数")
    private Integer amount;

    @ApiModelProperty(value = "日期")
    private Date createDate;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "拼音码")
    private String namePhonetic;

    @ApiModelProperty(value = "年龄")
    private Integer age;

    @ApiModelProperty(value = "性别")
    private String sexCode;

    @ApiModelProperty(value = "身份证号")
    private String idCard;

    @ApiModelProperty(value = "民族")
    private String nation;

    @ApiModelProperty(value = "BIRTH_PLACE")
    private String birthPlace;

    @ApiModelProperty(value = "文化程度")
    private String kultur;

    @ApiModelProperty(value = "婚姻状况")
    private String maritalStatus;

    @ApiModelProperty(value = "总工龄")
    private Integer totalLengthOfServer;

    @ApiModelProperty(value = "接害工龄")
    private Integer nowLengthOfServer;

    @ApiModelProperty(value = "毒害种类和名称")
    private String occupationHarm;

    @ApiModelProperty(value = "劳动合同")
    private String workingPoct;

    @ApiModelProperty(value = "操作员")
    private String dbUser;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "QRBZ")
    private String qrbz;

    @ApiModelProperty(value = "创建人")
    private String createId;

    @ApiModelProperty(value = "更新人")
    private String modifyId;

    @ApiModelProperty(value = "创建日期")
    private Date createdate;

    @ApiModelProperty(value = "更新日期")
    private Date modifydate;
}
