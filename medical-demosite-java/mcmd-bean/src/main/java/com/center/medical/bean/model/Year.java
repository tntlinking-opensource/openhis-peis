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
 * 年份表(Year)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:52:19
 */
@Data
@TableName("md_year")
@ApiModel(value = "Year", description = "年份表实体类")
public class Year extends Model<Year> implements Serializable {
    private static final long serialVersionUID = 346399503471861736L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "年份")
    private String year;

    @ApiModelProperty(value = "父节点")
    private String parentname;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "修改时间")
    private Date modifydate;
}
