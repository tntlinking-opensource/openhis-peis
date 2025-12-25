package com.center.medical.data.bean.model;

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
 * 创建团体客户要选择的所属行业在这里维护(Sshy)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-19 14:11:08
 */
@Data
@TableName("md_sshy")
@ApiModel(value = "Sshy", description = "创建团体客户要选择的所属行业在这里维护实体类")
public class Sshy extends Model<Sshy> implements Serializable {
    private static final long serialVersionUID = 560691441344986912L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "行业名称")
    private String hymc;

    @ApiModelProperty(value = "创建日期")
    private Date createdate;

    @ApiModelProperty(value = "修改日期")
    private Date modifydate;
}
