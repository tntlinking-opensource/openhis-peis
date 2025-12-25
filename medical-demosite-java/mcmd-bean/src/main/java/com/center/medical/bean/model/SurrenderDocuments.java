package com.center.medical.bean.model;


import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 交单记录表(SurrenderDocuments)表实体类
 *
 * @author ay
 * @since 2024-01-04 15:58:02
 */
@Data
@TableName("md_surrender_documents")
@ApiModel(value = "SurrenderDocuments", description = "交单记录表实体类")
public class SurrenderDocuments extends Model<SurrenderDocuments> implements Serializable {
    private static final long serialVersionUID = -16695293629084800L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;


    @ApiModelProperty(value = "分中心id")
    private String fzxid;


    @ApiModelProperty(value = "姓名")
    private String patientname;


    @ApiModelProperty(value = "体检号")
    private String patientcode;


    @ApiModelProperty(value = "提交人id")
    private String submitId;


    @ApiModelProperty(value = "提交人")
    private String presenter;

    @ApiModelProperty(value = "交单时间")
    private Date submitdate;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建日期")
    private Date createdate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新日期")
    private Date modifydate;


    public SurrenderDocuments(String fzxid, String patientname, String patientcode) {
        this.fzxid = fzxid;
        this.patientname = patientname;
        this.patientcode = patientcode;
    }


    public SurrenderDocuments() {
    }
}
