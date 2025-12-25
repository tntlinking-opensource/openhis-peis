package com.center.medical.datamove.common.bean.model;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 预约各检区默认设置(MdReservationDefault)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:48:18
 */
@Data
@TableName("md_reservation_default")
@ApiModel(value = "MdReservationDefault", description = "预约各检区默认设置实体类")
public class MdReservationDefault extends Model<MdReservationDefault> implements Serializable {
    private static final long serialVersionUID = 603144482358818187L;

    @TableId(value = "id")
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "时间段开始时间")
    private String startTime;

    @ApiModelProperty(value = "时间段结束时间")
    private String endTime;

    @ApiModelProperty(value = "人数上限")
    private Integer maxNum;

    @ApiModelProperty(value = "预约类型")
    private Integer levelId;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "分中心ID")
    private String branchId;

    @ApiModelProperty(value = "开放日，周一至周天，如：1,3,5,7 表示开放时间是周一三五七，二四六不开放")
    private String openDay;

    @ApiModelProperty(value = "状态：0.关闭 1.开放")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "创建者id")
    private String creator;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "更新者id")
    private String modifier;
}
