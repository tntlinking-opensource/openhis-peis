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
 * TJ综合分析-检出人数（职业）(FxDetectionzy)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:51:59
 */
@Data
@TableName("md_fx_detectionzy")
@ApiModel(value = "FxDetectionzy", description = "TJ综合分析-检出人数（职业）实体类")
public class FxDetectionzy extends Model<FxDetectionzy> implements Serializable {
    private static final long serialVersionUID = 293762461582430881L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "修改时间")
    private Date modifydate;

    @ApiModelProperty(value = "样本ID")
    private String sampleId;

    @ApiModelProperty(value = "疾病名称")
    private String disease;

    @ApiModelProperty(value = "检出人数")
    private Integer checkNum;

    @ApiModelProperty(value = "建议")
    private String occupationDiseast;

    @ApiModelProperty(value = "危害因素ID")
    private String occupationDiagnosis;

    @ApiModelProperty(value = "危害因素名称")
    private String harmName;

    @ApiModelProperty(value = "危害因素接触人数")
    private Integer harmNum;

    public FxDetectionzy(String sampleId, String disease, Integer checkNum, String occupationDiseast, String occupationDiagnosis, String harmName, Integer harmNum) {
        super();
        this.sampleId = sampleId;
        this.disease = disease;
        this.checkNum = checkNum;
        this.occupationDiseast = occupationDiseast;
        this.occupationDiagnosis = occupationDiagnosis;
        this.harmName = harmName;
        this.harmNum = harmNum;
    }

    public FxDetectionzy() {
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public Date getModifydate() {
        return modifydate;
    }

    public void setModifydate(Date modifydate) {
        this.modifydate = modifydate;
    }

    public String getSampleId() {
        return sampleId;
    }

    public void setSampleId(String sampleId) {
        this.sampleId = sampleId;
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public Integer getCheckNum() {
        return checkNum;
    }

    public void setCheckNum(Integer checkNum) {
        this.checkNum = checkNum;
    }

    public String getOccupationDiseast() {
        return occupationDiseast;
    }

    public void setOccupationDiseast(String occupationDiseast) {
        this.occupationDiseast = occupationDiseast;
    }

    public String getOccupationDiagnosis() {
        return occupationDiagnosis;
    }

    public void setOccupationDiagnosis(String occupationDiagnosis) {
        this.occupationDiagnosis = occupationDiagnosis;
    }

    public String getHarmName() {
        return harmName;
    }

    public void setHarmName(String harmName) {
        this.harmName = harmName;
    }

    public Integer getHarmNum() {
        return harmNum;
    }

    public void setHarmNum(Integer harmNum) {
        this.harmNum = harmNum;
    }
}
