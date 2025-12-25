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
 * 企业经济类型(Qyjjlx)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:52:10
 */
@Data
@TableName("md_qyjjlx")
@ApiModel(value = "Qyjjlx", description = "企业经济类型实体类")
public class Qyjjlx extends Model<Qyjjlx> implements Serializable {
    private static final long serialVersionUID = -57208421277875482L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "创建日期")
    private Date createdate;

    @ApiModelProperty(value = "修改时间")
    private Date modifydate;

    @ApiModelProperty(value = "企业经济类型名称")
    private String qyjjlx;
}
