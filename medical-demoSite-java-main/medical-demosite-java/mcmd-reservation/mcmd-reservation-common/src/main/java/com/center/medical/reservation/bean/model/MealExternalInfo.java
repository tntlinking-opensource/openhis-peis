package com.center.medical.reservation.bean.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;
import java.util.Date;

/**
 * 套餐第三方关联信息表(MealExternalInfo)表实体类
 *
 * @author makejava
 * @since 2024-01-11 09:36:29
 */
@Data
@TableName("md_meal_external_info")
@ApiModel(value="MealExternalInfo",description="套餐第三方关联信息表实体类")
public class MealExternalInfo extends Model<MealExternalInfo> implements Serializable {
	private static final long serialVersionUID = 698622049712974958L;

	@TableId(value="id")
	@ApiModelProperty(value = "id")
	private Integer id;

    @ApiModelProperty(value = "体检系统套餐ID")
	private String mealId;

    @ApiModelProperty(value = "记录时间")
	private Date createdate;

    @ApiModelProperty(value = "更新时间")
	private Date modifydate;

    @ApiModelProperty(value = "第三方ID")
	private String sourceId;

    @ApiModelProperty(value = "第三方套餐ID")
	private String bizComboId;
}
