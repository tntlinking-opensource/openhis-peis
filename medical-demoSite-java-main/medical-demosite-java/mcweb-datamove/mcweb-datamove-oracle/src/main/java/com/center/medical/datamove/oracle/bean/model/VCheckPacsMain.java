package com.center.medical.datamove.oracle.bean.model;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * (VCheckPacsMain)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:25:50
 */
@Data
@TableName("v_Check_PACS_Main")
@ApiModel(value = "VCheckPacsMain", description = "$tableInfo.comment实体类")
public class VCheckPacsMain extends Model<VCheckPacsMain> implements Serializable {
    private static final long serialVersionUID = 389183478647423670L;

    @TableId(value = "CheckReqID")
    @ApiModelProperty(value = "检查流水号(申请单号)")
    private String checkreqid;

    @ApiModelProperty(value = "体检登记编号")
    private String checkregno;

    @ApiModelProperty(value = "申请日期")
    private Date checkregdate;

    @ApiModelProperty(value = "检查日期")
    private Date checkdate;

    @ApiModelProperty(value = "检查技师工号")
    private String checkdoctorcode;

    @ApiModelProperty(value = "检查技师姓名")
    private String checkdoctorname;

    @ApiModelProperty(value = "PACS报告编号")
    private String reportno;

    @ApiModelProperty(value = "PACS报告日期")
    private Date reprtdate;

    @ApiModelProperty(value = "PACS报告医生工号")
    private String reportdoctorcode;

    @ApiModelProperty(value = "PACS报告医生")
    private String reprtdoctorname;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "性别")
    private String sex;

    @ApiModelProperty(value = "检验项目编号")
    private String itemcode;

    @ApiModelProperty(value = "检验项目名称")
    private String itemname;

    @ApiModelProperty(value = "申请科室")
    private String departname;

    @ApiModelProperty(value = "申请医生")
    private String doctorname;

    @ApiModelProperty(value = " 检查类型")
    private String checkreqtypecode;

    @ApiModelProperty(value = "检查部位")
    private String checkpartname;

    @ApiModelProperty(value = "影像诊断")
    private String imagediag;

    @ApiModelProperty(value = "影像所见")
    private String imageresult;

    @ApiModelProperty(value = "检查结果")
    private String checkresult;

    @ApiModelProperty(value = "检查描述")
    private String checkdesc;

    @ApiModelProperty(value = "其他描述")
    private String otherdesc;

    @ApiModelProperty(value = "影像报告文件路径")
    private String reportpath;

    @ApiModelProperty(value = "影像图片文件路径")
    private String imagepath;
}
