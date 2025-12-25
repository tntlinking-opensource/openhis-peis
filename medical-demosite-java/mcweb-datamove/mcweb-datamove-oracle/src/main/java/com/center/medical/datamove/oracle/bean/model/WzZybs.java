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
 * KS问诊——职业病史(WzZybs)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:30:52
 */
@Data
@TableName("WZ_ZYBS")
@ApiModel(value = "WzZybs", description = "KS问诊——职业病史实体类")
public class WzZybs extends Model<WzZybs> implements Serializable {
    private static final long serialVersionUID = 609659629339073671L;

    @ApiModelProperty(value = "登记流水")
    private String djls;

    @ApiModelProperty(value = "档案号")
    private String idPatientarchive;

    @ApiModelProperty(value = "体检次数")
    private String amount;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "序号")
    private String id;

    @ApiModelProperty(value = "日期")
    private Date createDate;

    @ApiModelProperty(value = "疾病名称")
    private String occupationDiseast;

    @ApiModelProperty(value = "诊断日期")
    private Date diagnosisDate;

    @ApiModelProperty(value = "诊断单位")
    private String diagnosisDept;

    @ApiModelProperty(value = "转归")
    private String diagnosisDesc;

    @ApiModelProperty(value = "操作员")
    private String dbUser;

    @ApiModelProperty(value = "治疗经过")
    private String remark;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "创建人")
    private String createId;

    @ApiModelProperty(value = "更新人")
    private String modifyId;

    @ApiModelProperty(value = "是否痊愈")
    private Integer status;
}
