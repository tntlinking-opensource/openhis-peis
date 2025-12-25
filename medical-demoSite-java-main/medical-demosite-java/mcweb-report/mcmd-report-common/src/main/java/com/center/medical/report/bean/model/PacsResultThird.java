package com.center.medical.report.bean.model;


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
 * 易影报告回传(PacsResultThird)表实体类
 *
 * @author makejava
 * @since 2025-08-14 10:15:32
 */
@Data
@TableName("md_pacs_result_third")
@ApiModel(value="PacsResultThird",description="易影报告回传实体类")
public class PacsResultThird extends Model<PacsResultThird> implements Serializable {
	private static final long serialVersionUID = -65688911521686349L;

	@TableId(value="id", type = IdType.ASSIGN_ID)
	@ApiModelProperty(value = "唯一值(md_peispatientfeeitem表id)")
	private String id;

    @ApiModelProperty(value = "体检号（8位）")
	private String patientId;

    @ApiModelProperty(value = "姓名")
	private String patientname;

    @ApiModelProperty(value = "检查结果描述")
	private String examresultdesc;

    @ApiModelProperty(value = "检查结果总结")
	private String examresultsummary;

    @ApiModelProperty(value = "检查时间")
	private Date examdatetime;

    @ApiModelProperty(value = "1阳性，0阴性")
	private Integer isPositive;

    @ApiModelProperty(value = "报告医生")
	private String reportDoctor;

    @ApiModelProperty(value = "审核医生")
	private String auditDoctor;

    @ApiModelProperty(value = "已申请、已报告/未审核、已审核")
	private String reportStatus;

	@ApiModelProperty(value = "云胶片二维码内容")
    private String qrCodeContent;
}
