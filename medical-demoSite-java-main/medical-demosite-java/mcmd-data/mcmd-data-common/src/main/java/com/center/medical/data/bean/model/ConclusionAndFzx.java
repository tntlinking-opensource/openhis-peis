package com.center.medical.data.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * JC结伦词和分中心关联表(ConclusionAndFzx)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:51:56
 */
@Data
@TableName("md_conclusion_and_fzx")
@ApiModel(value = "ConclusionAndFzx", description = "JC结伦词和分中心关联表实体类")
public class ConclusionAndFzx extends Model<ConclusionAndFzx> implements Serializable {
    private static final long serialVersionUID = -52872171751234390L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createdate;

    @ApiModelProperty(value = "修改时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifydate;

    @ApiModelProperty(value = "结伦词ID")
    private String conclusionId;

    @ApiModelProperty(value = "分中心ID")
    private String fzxId;

    @ApiModelProperty(value = "同步状态：0.未同步 1.已同步 ")
    private Integer tbzt;


    public ConclusionAndFzx(String id, Date createdate, String conclusionId, String fzxId, Integer tbzt) {
        super();
        this.id = id;
        this.createdate = createdate;
        this.conclusionId = conclusionId;
        this.fzxId = fzxId;
        this.tbzt = tbzt;
    }


    public ConclusionAndFzx() {

    }
}
