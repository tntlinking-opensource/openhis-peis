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
 * 体检者异常忽视关联表(PeispatientAbnormalIgnore)表实体类
 *
 * @author ay
 * @since 2022-11-18 14:46:10
 */
@Data
@TableName("md_peispatient_abnormal_ignore")
@ApiModel(value = "PeispatientAbnormalIgnore", description = "体检者异常忽视关联表")
public class PeispatientAbnormalIgnore extends Model<PeispatientAbnormalIgnore> implements Serializable {
    private static final long serialVersionUID = -29861292141028885L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "体检者表id")
    private String patientId;

    @ApiModelProperty(value = "创建人")
    private String creator;

    public PeispatientAbnormalIgnore(Date createdate, String patientId, String creator) {
        this.createdate = createdate;
        this.patientId = patientId;
        this.creator = creator;
    }


    public PeispatientAbnormalIgnore() {
    }
}
