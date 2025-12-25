package com.center.medical.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 危害因素和分中心(HarmAndFzx)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:52:00
 */
@Data
@TableName("md_harm_and_fzx")
@ApiModel(value = "HarmAndFzx", description = "危害因素和分中心实体类")
public class HarmAndFzx extends Model<HarmAndFzx> implements Serializable {
    private static final long serialVersionUID = 364640131482972092L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "收费项目ID")
    private String harmId;

    @ApiModelProperty(value = "分中心ID")
    private String fzxId;

    @ApiModelProperty(value = "同步状态：0.未同步 1.已同步")
    private Integer tbzt;


    public HarmAndFzx(String harmId, String fzxId, Integer tbzt, Date createdate) {
        super();
        this.harmId = harmId;
        this.fzxId = fzxId;
        this.tbzt = tbzt;
        this.createdate = createdate;
    }

    public HarmAndFzx() {
    }
}
