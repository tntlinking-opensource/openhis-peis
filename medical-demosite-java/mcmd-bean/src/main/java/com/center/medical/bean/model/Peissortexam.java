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
 * 排检(Peissortexam)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:52:10
 */
@Data
@TableName("md_peissortexam")
@ApiModel(value = "Peissortexam", description = "排检实体类")
public class Peissortexam extends Model<Peissortexam> implements Serializable {
    private static final long serialVersionUID = 120362917363673231L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "团体分组id")
    private String groupId;

    @ApiModelProperty(value = "天")
    private Date sortDate;

    @ApiModelProperty(value = "订单id")
    private String orderId;

    @ApiModelProperty(value = "序号")
    private Integer sortNum;

    @ApiModelProperty(value = "套餐id")
    private String tcid;

    @ApiModelProperty(value = "上午人数")
    private Integer am;

    @ApiModelProperty(value = "下午人数")
    private Integer pm;

    @ApiModelProperty(value = "预约人用户名")
    private String creator;

    @ApiModelProperty(value = "备注")
    private String bz;
}
