package com.center.medical.center.common.bean.model;
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
 * 对接瑞林萨尔健康管理系统,记录数据上传结果(RilinSyncResult)表实体类
 * @since 2025-03-21 14:46:36
 */
@Data
@TableName("rilin_sync_result")
@ApiModel(value="RilinSyncResult",description="对接瑞林萨尔健康管理系统,记录数据上传结果实体类")
public class RilinSyncResult extends Model<RilinSyncResult> implements Serializable {
	private static final long serialVersionUID = -15124983685505331L;

    @TableId(value="id", type = IdType.ASSIGN_ID)
	@ApiModelProperty(value = "ID")
	private String id;

    @ApiModelProperty(value = "创建时间")
	private Date createdate;

    @ApiModelProperty(value = "修改时间")
	private Date modifydate;

    @ApiModelProperty(value = "1上传成功 0上传失败")
	private Integer isSuccess;

    @ApiModelProperty(value = "上传失败的错误信息")
	private String errorMsg;

    @ApiModelProperty(value = "表名")
	private String tableName;

    @ApiModelProperty(value = "上传失败的表ID  没用到")
	private String tableId;

    @ApiModelProperty(value = "上传失败的体检号  没用到")
	private String patientcode;

    @ApiModelProperty(value = "本次上传起始时间")
	private Date startTime;

    @ApiModelProperty(value = "本次上传止时间")
	private Date endTime;

    @ApiModelProperty(value = "备注")
	private String remarks;
}
