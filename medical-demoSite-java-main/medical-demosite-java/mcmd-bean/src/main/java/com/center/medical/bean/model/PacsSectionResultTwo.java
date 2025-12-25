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
 * PACS-科室结果表(PacsSectionResultTwo)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:52:09
 */
@Data
@TableName("md_pacs_section_result_two")
@ApiModel(value = "PacsSectionResultTwo", description = "PACS-科室结果表实体类")
public class PacsSectionResultTwo extends Model<PacsSectionResultTwo> implements Serializable {
    private static final long serialVersionUID = -26831685642938109L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "主表ID")
    private String mainId;

    @ApiModelProperty(value = "PACS检查项目ID")
    private String verdictId;

    @ApiModelProperty(value = "PACS检查项目体证词关联表ID")
    private String nodule;

    @ApiModelProperty(value = "否阳性结果(1阳性  0或NULL非阳性)")
    private Integer posistive;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "重症级别(弃用)")
    private Integer intensiveLevel;

    @ApiModelProperty(value = "是否弃检(弃用)")
    private Integer isUnchecked;

    @ApiModelProperty(value = "0.健康 1.职业")
    private Integer diseaseHealth;

    @ApiModelProperty(value = "PACS结论词ID")
    private String basconclusionId;

    @ApiModelProperty(value = "科室ID")
    private String divisionId;

    @ApiModelProperty(value = "是否删除：0.未删除 1.已删除")
    private Integer isDelete;

    @ApiModelProperty(value = "PACS收费项目ID")
    private String chargesId;

    @ApiModelProperty(value = "ms")
    private String ms;

    @ApiModelProperty(value = "是否危急值")
    private Integer isDanger;

    @ApiModelProperty(value = "输入的结果(弃用)")
    private String inputResult;

    @ApiModelProperty(value = "体检类型 1.职业 0.非职业      1.职业+健康 0、纯健康 2、纯职业(只有检验科有)(弃用)")
    private Integer tjlx;

    @ApiModelProperty(value = "短体检号")
    private Integer shortCode;

    @ApiModelProperty(value = "PACS体检者收费项目ID")
    private String feeitemId;



    public PacsSectionResultTwo(String verdictId, String nodule,
                                Integer posistive, String patientcode, String basconclusion_id,
                                String division_id, String chargesId, Integer shortCode,
                                String feeitemId,Integer tjlx) {
        super();
        this.verdictId = verdictId;
        this.nodule = nodule;
        this.posistive = posistive;
        this.patientcode = patientcode;
        this.basconclusionId = basconclusion_id;
        this.divisionId = division_id;
        this.chargesId = chargesId;
        this.shortCode = shortCode;
        this.feeitemId = feeitemId;
        this.tjlx=tjlx;
    }


    public PacsSectionResultTwo() {
    }
}
