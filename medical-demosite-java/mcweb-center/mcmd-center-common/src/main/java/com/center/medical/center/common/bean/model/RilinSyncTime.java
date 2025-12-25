package com.center.medical.center.common.bean.model;


    import com.baomidou.mybatisplus.annotation.IdType;    import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;
import java.util.Date;

/**
 * 对接瑞林萨尔健康管理系统，用于记录上一次上传数据的止时间。(RilinSyncTime)表实体类
 * @since 2025-03-21 14:53:06
 */
@Data
@TableName("rilin_sync_time")
@ApiModel(value="RilinSyncTime",description="对接瑞林萨尔健康管理系统，用于记录上一次上传数据的止时间。实体类")
public class RilinSyncTime extends Model<RilinSyncTime> implements Serializable {
	private static final long serialVersionUID = 898450021296402064L;

	@TableId(value="id", type = IdType.ASSIGN_ID)
	@ApiModelProperty(value = "ID")
	private String id;

	@ApiModelProperty(value = "创建时间")
	private Date createdate;

	@ApiModelProperty(value = "修改时间")
	private Date modifydate;

	@ApiModelProperty(value = "表名")
	private String tableName;

	@ApiModelProperty(value = "上一次上传数据所用时间段的止时间")
	private Date lastSyncTime;
}
