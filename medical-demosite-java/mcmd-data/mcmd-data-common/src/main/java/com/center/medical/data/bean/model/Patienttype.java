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
 * 体检者类型(Patienttype)表实体类
 *
 * @author 路飞船长
 * @since 2022-12-07 19:27:27
 */
@Data
@TableName("md_patienttype")
@ApiModel(value = "Patienttype", description = "体检者类型实体类")
public class Patienttype extends Model<Patienttype> implements Serializable {
    private static final long serialVersionUID = -74909432250633800L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "名称")
    private String patientName;

    @ApiModelProperty(value = "输入码")
    private String inputCode;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "修改日期")
    private Date modifydate;

    @ApiModelProperty(value = "如果为1在科室显示红色名称")
    private Integer flag;
}
