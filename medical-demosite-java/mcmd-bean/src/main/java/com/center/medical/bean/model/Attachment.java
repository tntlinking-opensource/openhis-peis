package com.center.medical.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * JC附件(Attachment)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:51:54
 */
@Data
@TableName("md_attachment")
@ApiModel(value = "Attachment", description = "JC附件实体类")
public class Attachment extends Model<Attachment> implements Serializable {
    private static final long serialVersionUID = 141429335084894891L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "文件序列")
    private String fileSort;

    @ApiModelProperty(value = "文件路径")
    private String filePath;

    @ApiModelProperty(value = "文件大小")
    private Integer fileSize;

    @ApiModelProperty(value = "类型说明")
    private String fileType;

    @ApiModelProperty(value = "bw")
    private String bw;

    @ApiModelProperty(value = "dicom文件路径，高清屏使用，字段组成成分:dicom路径,窗宽,窗位")
    private String dcmPath;

    @ApiModelProperty(value = "备注")
    private String memo;

    @ApiModelProperty(value = "如果为空，取标识最小的attachmentConfig.保存文件时，用标识最大的CONFIG")
    private String configId;

    @ApiModelProperty(value = "是否进报告：0.否 1.是(PACS  彩超控制个检报告和科室报告)")
    private Integer inReport;

    @ApiModelProperty(value = "上传人")
    private String createId;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "短号体检号")
    private Integer shortCode;

    @ApiModelProperty(value = "体检者收费项目ID（PACS）")
    private String feeItemId;

    @ApiModelProperty(value = "PACS系统的体检者收费项目ID")
    private String pacsFeeItemId;

    @ApiModelProperty(value = "科室id")
    private String depId;

    @ApiModelProperty(value = "分中心ID")
    private String branchId;

    @ApiModelProperty(value = "文件类型：1.图片 2.视频 3.文件")
    private Integer type;

    @ApiModelProperty(value = "分组ID")
    private String fileGroupId;

    @ApiModelProperty(value = "文件状态：0.正常 1.待删除文件 2.已删除文件待同步（线上线下同步）")
    private Integer status;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "修改日期")
    private Date modifydate;


    public Attachment() {

    }
    public Attachment(String patientCode, String depId, String sort,
                          String path, Integer type, String creatId, String feeItemId,
                          Integer shortCode,String pacsFeeItemId,String configId,Integer inReport) {
        super();
        this.patientcode = patientCode;
        this.depId = depId;
        this.fileSort = sort;
        this.filePath = path;
        this.type = type;
        this.createId = creatId;
        this.feeItemId = feeItemId;
        this.shortCode = shortCode;
        this.pacsFeeItemId=pacsFeeItemId;
        this.configId=configId;
        this.inReport=inReport;
    }
}
