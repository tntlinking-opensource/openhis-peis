package com.center.medical.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * KS问诊——职业病史(WzZybs)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:52:18
 */
@Data
@TableName("md_wz_zybs")
@ApiModel(value = "WzZybs", description = "KS问诊——职业病史实体类")
public class WzZybs extends Model<WzZybs> implements Serializable {
    private static final long serialVersionUID = -18728682253798383L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "序号")
    private String id;

    @ApiModelProperty(value = "登记流水")
    private String djls;

    @ApiModelProperty(value = "档案号")
    private String idPatientarchive;

    @ApiModelProperty(value = "体检次数")
    private Integer amount;

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

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "创建人")
    private String createId;

    @ApiModelProperty(value = "更新人")
    private String modifyId;

    @ApiModelProperty(value = "是否痊愈")
    private Integer status;

    @TableField(exist = false)
    @ApiModelProperty(value = "职业病名称")
    private String occupationDiseastName;

}
